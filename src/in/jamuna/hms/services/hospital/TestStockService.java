package in.jamuna.hms.services.hospital;

import in.jamuna.hms.config.GlobalValues;
import in.jamuna.hms.dao.*;
import in.jamuna.hms.dao.hospital.ProceduresDAO;
import in.jamuna.hms.dao.hospital.TestCompanyDAO;
import in.jamuna.hms.dao.hospital.TestStockSpentDAO;
import in.jamuna.hms.dao.hospital.TestSupplierDAO;
import in.jamuna.hms.dto.MiniTestStockDTO;
import in.jamuna.hms.dto.common.CommonIdAndNameDto;
import in.jamuna.hms.dto.common.CommonWithDouble;
import in.jamuna.hms.entities.hospital.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class TestStockService {
    @Autowired
    private TestStockSpentDAO testStockSpentDAO;
    @Autowired
    private TestSupplierDAO testSupplierDAO;

    @Autowired
    private TestInvoiceDAO testInvoiceDAO;
    @Autowired
    private ProceduresDAO proceduresDAO;

    @Autowired
    private TestCompanyDAO testCompanyDAO;

    @Autowired
    private ConverterService converterService;

    @Autowired
    private
    AllocatedStockDAO allocateStockDAO;

    @Autowired
    private TestStockDAO testStockDAO;
    @Autowired
    private ProcedureProductDAO procedureProductDAO;

    @Autowired
    private TestProductDAO testProductDAO;


    private static final Logger LOGGER = Logger.getLogger(TestStockService.class.getName());


    public long getTotalCountByType(String type) {
        long count = 0;
        if(type.equals("supplier")){
            count =  testSupplierDAO.getCount();
        }else if(type.equals("company")){
            count =  testCompanyDAO.getCount();
        }else if(type.equals("product")){
            count =  testProductDAO.getCount();
        }

        LOGGER.info("cont:"+count);
        return count;
    }

    public List<CommonIdAndNameDto> getListByPageAndType(Integer no, String type, int perPage) {
        try{

            if(type.equals("supplier")){
                return testSupplierDAO.getByPage(no,perPage).stream().map(s->
                   new CommonIdAndNameDto(s.getId(),s.getName(),s.isEnabled())
                ).collect(Collectors.toList());
            }else if(type.equals("company")){
                return testCompanyDAO.getByPage(no,perPage).stream().map(s->
                        new CommonIdAndNameDto(s.getId(),s.getName(),s.isEnabled())
                ).collect(Collectors.toList());
            }
            else if(type.equals("product")){

                return testProductDAO.getByPage(no,perPage).stream().map(s->
                        new CommonIdAndNameDto(s.getId(),s.getName(),s.isEnabled())
                ).collect(Collectors.toList());
            }

        }catch (Exception e){
            LOGGER.info(e.toString());
        }
            return null;

    }

    @Transactional
    public void addByType(String type, String name) {

            if(type.equals("supplier")){
                testSupplierDAO.add(name);
            }else if(type.equals("company")){
                testCompanyDAO.add(name);
            }
    }

    @Transactional
    public boolean addInvoice(HttpServletRequest request){
        try {
            TestSupplierEntity supplier = testSupplierDAO.findById(
                    Integer.parseInt(request.getParameter("supplier"))
            ) ;
            Date invoiceDate = converterService.convert( request.getParameter("date") );
            String invoice = request.getParameter("invoice");
            TestBatchInvoiceEntity invoiceEntity=testInvoiceDAO.add( supplier, invoiceDate, invoice );
            int rows = Integer.parseInt( request.getParameter("rows") );

            for(int i=1;i<=rows;i++){
                int productId = Integer.parseInt( request.getParameter("product_"+i) );
                double amount = Double.parseDouble(request.getParameter("amount_"+i));
                String batch = request.getParameter("batch_"+i);
                double discount = Double.parseDouble(request.getParameter("discount_"+i));
                Date expiry = converterService.convert( request.getParameter("expiry_"+i) );
                int free = 0;
                try{
                    free = Integer.parseInt(request.getParameter("free_"+i));
                }catch (Exception e){
                    free = 0;
                }
                int quantity = Integer.parseInt( request.getParameter("qty_"+i) );
                double tax = Double.parseDouble(request.getParameter("tax_"+i));
                double rate = Double.parseDouble(request.getParameter("rate_"+i));
                double mrp = Double.parseDouble(request.getParameter("mrp_"+i));
                testStockDAO.add(invoiceEntity,
                        testProductDAO.findById(productId),
                        amount, batch, discount, expiry, free, quantity, tax,rate,mrp
                );

            }
            return true;
        }catch (Exception e){
            LOGGER.info(e.toString());
        }
        return false;

    }


    public void toggleByType(String type, Integer id, String action) {
        if(type.equals("supplier")){
            if(action.equals("enable"))
                testSupplierDAO.toggle(id,true);
            else if(action.equals("disable"))
                testSupplierDAO.toggle(id,false);
        }else if(type.equals("company")){
            if(action.equals("enable"))
                testCompanyDAO.toggle(id,true);
            else if(action.equals("disable"))
                testCompanyDAO.toggle(id,false);
        }else if(type.equals("product")){
            if(action.equals("enable"))
                testProductDAO.toggle(id,true);
            else if(action.equals("disable"))
                testProductDAO.toggle(id,false);
        }
    }

    public List<CommonIdAndNameDto> getCommonByName(String term, String type) {
        if(type.equals("company")){
            return testCompanyDAO.findByNameAndLimit(term, GlobalValues.getSearchlimit()).stream().map(s->
                    new CommonIdAndNameDto(s.getId(),s.getName(),s.isEnabled())
            ).collect(Collectors.toList());
        }else if (type.equals("product")){
            return testProductDAO.findByNameAndLimit(term, GlobalValues.getSearchlimit()).stream().map(s->
                    new CommonIdAndNameDto(s.getId(),s.getName(),s.isEnabled())
            ).collect(Collectors.toList());
        } else if (type.equals("supplier")) {
            return testSupplierDAO.findByNameAndLimit(term, GlobalValues.getSearchlimit()).stream().map(s->
                    new CommonIdAndNameDto(s.getId(),s.getName(),s.isEnabled())
            ).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public void addProduct(String name, Integer id) {
        try{
            testProductDAO.add(name, testCompanyDAO.findById(id) );

        }catch (Exception e){
            LOGGER.info(e.toString());
        }
    }

    public void addMapping(Integer productId, Integer procedureId, int ratio) {
        try {

            procedureProductDAO.add(
                    testProductDAO.findById(productId),
                    proceduresDAO.findById(procedureId),
                    ratio
            );
        }
        catch (Exception e){
            LOGGER.info(e.getMessage());
        }
    }

    public void changeRatio(int id, int ratio) {
        procedureProductDAO.changeRatioById( id, ratio );
    }

    public void deleteByType(int id, String type) {
        if(type.equals("stock-mapping")){
            procedureProductDAO.delete(id);
        }
    }

    public List<CommonWithDouble> getBatchesForAllocate(int product, int qty) {
        List<TestStockEntity> list = testStockDAO.getBatchesForAllocateAndExpirySortAndExpiryGap(
                testProductDAO.findById(product), qty, "", GlobalValues.getEXPIRY_MARGIN()
        );
        double sum = list.stream().map(m->m.getQtyLeft()).reduce(0.0, (a, b) -> a+ b);
        if(sum >= qty){
            sum = sum - list.get(list.size()-1).getQtyLeft();
            list.get(list.size()-1).setQtyLeft( qty - sum );
        }
        return list.stream().map(s -> {
        CommonWithDouble dto = new CommonWithDouble();
        dto.setExpiry(s.getExpiry());
        dto.setId(s.getId()); dto.setName(s.getBatch());dto.setNo(s.getQtyLeft());
            return dto;
               }
      ).collect(Collectors.toList());
    }

    @Transactional
    public boolean allocateStock(int id, double qty) {
        try{
            List<TestStockEntity> list = testStockDAO.getBatchesForAllocateAndExpirySortAndExpiryGap(
                    testProductDAO.findById(id), qty, "", GlobalValues.getEXPIRY_MARGIN()
            );
            double sum = 0;
            int size = list.size();
            for(int i=0;i<size-1;i++){
                TestStockEntity l = list.get(i);
                    sum+=l.getQtyLeft();

                    //add new allocatestock
                    allocateStockDAO.add( new Date(), l, l.getQtyLeft() );
                    //reduce qtyLeft to 0
                    l.setQtyLeft(0.0);
            }
            TestStockEntity last = list.get(size-1);
            double need = qty-sum;
            allocateStockDAO.add(new Date(), last,need );
            last.setQtyLeft(last.getQtyLeft() - need);
            return true;
        }catch (Exception e){
            LOGGER.info(e.toString());
        }




        return false;
    }

    public boolean checkLowStock(ProcedureRatesEntity item) {
        boolean flag = false;
        try {
            List<ProcedureProductMappingEntity> list = item.getMappings();

            if(list!=null){
                //get all mappings
                for(ProcedureProductMappingEntity mapping:  list ){

                    int ratio = mapping.getRatio();
                    //get allocated stock by product

                    TestProductEntity prod = mapping.getProduct();

                    Double sum =  allocateStockDAO.getSumOfStockByProd(prod);
                    if(sum == null){

                        flag = true;
                        break;
                    }
                    else{
                        LOGGER.info(sum+":sum");
                        if( (sum- (1.0/ratio)) < 0.0 ){
                            flag = true;
                            break;
                        }
                    }
                }

            }else {
                flag = true;
            }
        }catch (Exception e){
            LOGGER.info(e.getMessage());
            flag = true;
        }
        return flag;
    }

    @Transactional
    public void addStockSpent(ProcedureBillItemEntity billItem) {
        //reduce from allocated and create stock spent
        List<ProcedureProductMappingEntity> mappings = billItem.getProcedure().getMappings();
        for(ProcedureProductMappingEntity map:mappings){
            //aelect from allocated where stock.prod = map.getProd and qty_left > 0
           List<AllocatedStockEntity> allocated =  allocateStockDAO.findByQtyLeftAndStockProd( 0.0, map.getProduct() );
            //list of allocated stock with prod and qtyLef>0.0

            double needed = 1.0/map.getRatio();
            int ind = 0;
            //
            while(needed > 0.0 && ind < allocated.size() ){
                LOGGER.info("needed:"+needed);
                AllocatedStockEntity l = allocated.get(ind);

                //qty left = 0.3 ratio = 0.3 - done,done
                //qty left = 0.4 ratio = 0.3 - done,done
                // 2 itr, qty1=0.3,qty2=0.3, ratio = 0.6 - done,
                // 2 itr, qty1=0.3,qty2=0.3, ratio = 0.5 - done
                if( needed <= l.getQtyLeft() ){
                    testStockSpentDAO.add( billItem, l, needed );
                    l.setQtyLeft( l.getQtyLeft() - needed );
                }else{
                    // needed > qtyleft
                    testStockSpentDAO.add(billItem,l,l.getQtyLeft());
                    l.setQtyLeft(0.0);
                }
                needed-=l.getQtyLeft();
                ind++;

            }



        }

    }

    public List<MiniTestStockDTO> viewStockByProduct(int id) {
        try{
            LOGGER.info(testStockDAO.findByProduct(
                    testProductDAO.findById(id)).size()+":size");
            return testStockDAO.findByProduct(
                    testProductDAO.findById(id)
            ).stream().map(m -> converterService.convert(m) ).collect(Collectors.toList());
        }catch (Exception e){
            LOGGER.info(e.toString());
        }
        return new ArrayList<>();

    }

    public List<CommonWithDouble> getAllocatedStockOrderByProduct() {
        List<CommonWithDouble> list = new ArrayList<>();
        List<TestProductEntity> prods = testProductDAO.findAll();
        for(TestProductEntity prod:prods){
            CommonWithDouble dto = new CommonWithDouble();
            dto.setId(prod.getId());
            dto.setName(prod.getName());
            dto.setNo( allocateStockDAO.findByQtyLeftAndStockProd(0.0,prod).stream().map(m->m.getQtyLeft()).reduce( 0.0,(a,b) -> a+b ) );
            list.add(dto);
        }
        return list;
    }
}
