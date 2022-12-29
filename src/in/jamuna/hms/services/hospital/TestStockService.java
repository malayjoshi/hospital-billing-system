package in.jamuna.hms.services.hospital;

import in.jamuna.hms.config.GlobalValues;
import in.jamuna.hms.dao.hospital.billing.ProcedureBillItemDAO;
import in.jamuna.hms.dao.hospital.billing.ProceduresDAO;
import in.jamuna.hms.dao.hospital.stock.*;
import in.jamuna.hms.dto.cart.BillDTO;
import in.jamuna.hms.dto.cart.CartItemDTO;
import in.jamuna.hms.dto.common.ProductForInvoice;
import in.jamuna.hms.dto.reports.CommonIdAndNameWithDoubleListDTO;
import in.jamuna.hms.dto.reports.MiniTestStockDTO;
import in.jamuna.hms.dto.common.CommonIdAndNameDto;
import in.jamuna.hms.dto.common.CommonWithDouble;
import in.jamuna.hms.dto.reports.PerTestProductStockInfo;

import in.jamuna.hms.dto.reports.SpentStockSummaryDTO;
import in.jamuna.hms.entities.hospital.billing.ProcedureBillItemEntity;
import in.jamuna.hms.entities.hospital.billing.ProcedureRatesEntity;
import in.jamuna.hms.entities.hospital.stock.*;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class TestStockService {

    @Autowired
    BillingService billingService;
    @Autowired
    private TestStockSpentDAO testStockSpentDAO;
    @Autowired
    private TestSupplierDAO testSupplierDAO;

    @Autowired
    private TestInvoiceDAO testInvoiceDAO;
    @Autowired
    private ProceduresDAO proceduresDAO;

    @Autowired
    private ProcedureBillItemDAO billItemDao;

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

    public List<ProductForInvoice> getListByPageAndType(Integer no, String type, int perPage) {
        try{

            if(type.equals("supplier")){
                return testSupplierDAO.getByPage(no,perPage).stream().map(s1->
                        {
                            ProductForInvoice dto1 = new ProductForInvoice();
                            dto1.setEnabled(s1.isEnabled());dto1.setName(s1.getName());dto1.setId(s1.getId());
                            return dto1;
                        }
                ).collect(Collectors.toList());
            }else if(type.equals("company")){
                return testCompanyDAO.getByPage(no,perPage).stream().map(s2->{
                    ProductForInvoice dto2 = new ProductForInvoice();
                    dto2.setEnabled(s2.isEnabled());dto2.setName(s2.getName());dto2.setId(s2.getId());
                    return dto2;
                        }

                ).collect(Collectors.toList());
            }
            else if(type.equals("product")){

                return testProductDAO.getByPage(no,perPage).stream().map(s3->
                        {
                            ProductForInvoice dto3 = new ProductForInvoice();
                            dto3.setEnabled(s3.isEnabled());dto3.setName(s3.getName());dto3.setId(s3.getId());
                            dto3.setCompany( testProductDAO.getCompanyByProdId(s3.getId()).getName() );
                            return dto3;
                        }
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
                String batch = request.getParameter("batch_"+i);
                Date expiry = converterService.convert( request.getParameter("expiry_"+i) );
                int free = 0;
                try{
                    free = Integer.parseInt(request.getParameter("free_"+i));
                }catch (Exception e){
                    free = 0;
                }
                int quantity = Integer.parseInt( request.getParameter("qty_"+i) );
                testStockDAO.add(invoiceEntity,
                        testProductDAO.findById(productId),
                         batch, expiry, free, quantity
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
                testProductDAO.findById(product), qty, "", GlobalValues.getEXPIRY_MARGIN_LAB()
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
                    testProductDAO.findById(id), qty, "", GlobalValues.getEXPIRY_MARGIN_LAB()
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


                double temp;
                if( needed <= l.getQtyLeft() ){
                    testStockSpentDAO.add( billItem, l, needed );
                    temp =l.getQtyLeft();
                    l.setQtyLeft( l.getQtyLeft() - needed );

                }else{
                    // needed > qtyleft
                    testStockSpentDAO.add(billItem,l,l.getQtyLeft());
                    temp=l.getQtyLeft();
                    l.setQtyLeft(0.0);
                }
                needed-=temp;
                ind++;

            }



        }

    }

    public List<MiniTestStockDTO> viewStockByProduct(int id) {
        try{

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
            dto.setNo(Math.round(dto.getNo()*1000)/1000.0);
            list.add(dto);
        }
        return list;
    }

    public List<SpentStockSummaryDTO> getSpentStockByTypeAndDate(String type, Date date) {
        try {
            List<TestStockSpentEntity> list = new ArrayList<>();
            if(type.equals("Daily")){
               list = testStockSpentDAO.findByStartAndEndDate(date,date);
            } else if (type.equals("Monthly")) {
                DateTime dt1 = new DateTime(date);
                Date startDate =  dt1.withDayOfMonth(1).toDate();

                DateTime dt2 = new DateTime(date);
                Date endDate = dt2.plusMonths(1).withDayOfMonth(1).minusDays(1).toDate();
                list = testStockSpentDAO.findByStartAndEndDate(startDate,endDate);

            }

            List<SpentStockSummaryDTO> dto = new ArrayList<>();

            List<TestProductEntity> productEntities = testProductDAO.findAll();
            for( TestProductEntity prod:productEntities ){
                SpentStockSummaryDTO item = new SpentStockSummaryDTO();
                item.setId(prod.getId());
                item.setName(prod.getName());

                double sum = list.stream().
                        filter( s -> testProductDAO.findBySpentStock(s)==prod.getId() )
                        .map(m -> m.getQty()).reduce(0.0,(a,b)->a+b);

                item.setQty(Math.round(sum*1000)/1000.0);

                List<CommonIdAndNameWithDoubleListDTO> procedures = new ArrayList<>();

                //get mappings
                for( ProcedureProductMappingEntity map: procedureProductDAO.findByProduct(prod) ){

                    CommonIdAndNameWithDoubleListDTO proc = new CommonIdAndNameWithDoubleListDTO();
                    ProcedureRatesEntity procedureRates = procedureProductDAO.findTestById(map.getId());
                    proc.setId(map.getId());
                    proc.setName( procedureRates.getProcedure() );

                    List<Double> values = new ArrayList<>();
                    double completedTestsBySpent = list.stream().
                            filter( spent -> spent.getItem().getProcedure().getId()==procedureRates.getId() ).
                            map( spent -> spent.getQty() ).count();
                    double totalRatio = list.stream().
                            filter( spent -> spent.getItem().getProcedure().getId()==procedureRates.getId() && spent.getQty()>0.0 ).
                            map( spent -> 1.0/spent.getQty() ).reduce(0.0,(a,b)-> a+b);
                    double avgRatio = Math.round( totalRatio/completedTestsBySpent );
                    double totalSpent = list.stream().
                            filter( spent -> spent.getItem().getProcedure().getId()==map.getTest().getId() ).
                            map( spent -> spent.getQty() ).reduce(0.0,(a,b)-> a+b);

                    values.add(completedTestsBySpent);
                    values.add(avgRatio);
                    values.add(Math.round(totalSpent*1000)/1000.0);

                    proc.setValues(values);
                    procedures.add(proc);
                }

                item.setList(procedures);

                dto.add(item);
            }
            return dto;
        }catch (Exception e){
            LOGGER.info(e.getMessage());
        }
        return new ArrayList<>();
    }

    public List<PerTestProductStockInfo> getStockSummaryByPage(Integer no, int perPage) {

        List<PerTestProductStockInfo> list = new ArrayList<>();
        try{
            List<TestProductEntity> products = testProductDAO.getByPage(no,perPage);

            for(TestProductEntity product:products){
                PerTestProductStockInfo dto = new PerTestProductStockInfo();
                dto.setId(product.getId());
                dto.setProduct(product.getName());

                double totalStock = testStockDAO.findByProduct(product).stream().map(m->m.getQtyLeft()).reduce(0.0,(a,b)->a+b);
                dto.setTotalStock(
                        Math.round(totalStock*1000)/1000.0
                        );


                Date firstDayThisMonth = new DateTime().withDayOfMonth(1).toDate();
                double expired =
                        testStockDAO.findByProductAndLessThanExpiry(product,firstDayThisMonth).
                                stream().map(m->m.getQtyLeft()).reduce(0.0,(a,b)->a+b);

                dto.setExpired(
                        Math.round(expired*1000)/1000.0
                );

                dto.setEffectiveStock( dto.getTotalStock() - dto.getExpired() );

                double orgQty = testStockDAO.findByProductOnly(product).stream().map(m->m.getQty()).reduce(0.0,(a,b)->a+b);
                dto.setOrgStock(orgQty);


                List<AllocatedStockEntity> allocatedStock = allocateStockDAO.findByProd(product);
                double orgAllocated = allocatedStock
                        .stream().map(m->m.getQty()).reduce(0.0, (a, b) -> a+ b);
                double allocatedLeft = allocateStockDAO.findByProd(product)
                        .stream().map(m->m.getQtyLeft()).reduce(0.0, (a, b) -> a+ b);

                dto.setOrgAllocated(Math.round(orgAllocated*1000)/1000.0);
                dto.setAllocatedLeft(Math.round(allocatedLeft*1000)/1000.0);

                double spent = testStockSpentDAO.findByProduct(product)
                        .stream().map(m->m.getQty()).reduce(0.0, (a, b) -> a+ b);
                dto.setSpent(Math.round(spent*1000)/1000.0);

                list.add(dto);
            }

        }catch (Exception e){
            LOGGER.info(e.getMessage());
        }

        return list;
    }

    public List<ProductForInvoice> getProductsByTerm(String term) {
        return testProductDAO.getProductForInvoiceByTerm(term);
    }

    @Transactional
    public void setCompany(int id, int item) {
        testProductDAO.findById(item).setCompany( testCompanyDAO.findById(id) );
    }

    @Transactional
    public void truncateProductsAllocatedLeft(int id) {
        try {
            List<AllocatedStockEntity> left =  allocateStockDAO.findByProd(
                    testProductDAO.findById(id)
            ).stream().filter(m-> m.getQtyLeft() > 0.0).collect(Collectors.toList());

            for(AllocatedStockEntity alloc:left){
                testStockSpentDAO.add(null,alloc,alloc.getQtyLeft());
                alloc.setQtyLeft(0.0);
            }
        }catch (Exception e){
            LOGGER.info(e.toString());
        }


    }

    public List<CartItemDTO> findBillByTidAndSpent(int tid) {
        try {
            List<ProcedureBillItemEntity> billItemEntities = billItemDao.findByTid(tid);
            List<TestStockSpentEntity> items = testStockSpentDAO.findByTid( tid );

            List<CartItemDTO> dtos = new ArrayList<>();
            for(ProcedureBillItemEntity item:billItemEntities){
                CartItemDTO dto = new CartItemDTO();
                ProcedureRatesEntity proc = proceduresDAO.getByBillItem(item);
                dto.setName(proc.getProcedure());
                dto.setId(item.getId());
                dto.setStockTracking(proc.isStockEnabled());

                boolean found = false;

                for(TestStockSpentEntity spent:items){
                    if( spent.getItem().getId() == item.getId() ){
                        found = true;
                        break;
                    }
                }

                dto.setEnabled(found);
                dtos.add(dto);

            }
            return dtos;
        }catch (Exception e){
            LOGGER.info(e.toString());
        }


        return new ArrayList<>();
    }

    @Transactional
    public void revertTransactionByType(int tid, Integer id) {
        try{
            if(id != null){
                //get list of spent stock by item id
                List<TestStockSpentEntity> spentEntities = testStockSpentDAO.findByItemId(id);
                for(TestStockSpentEntity spent:spentEntities){
                    //get allocated
                    double qtyLeft = spent.getAllocatedStock().getQtyLeft();
                    qtyLeft+=spent.getQty();
                    spent.getAllocatedStock().setQtyLeft(qtyLeft);
                }

                testStockSpentDAO.deleteByItemId(id);
            } else {
                LOGGER.info(":size");
                List<TestStockSpentEntity> spentEntities = testStockSpentDAO.findByTid(tid);
                LOGGER.info(spentEntities.size()+":size");
                for(TestStockSpentEntity spent:spentEntities){
                    //get allocated
                    double qtyLeft = spent.getAllocatedStock().getQtyLeft();
                    qtyLeft+=spent.getQty();
                    spent.getAllocatedStock().setQtyLeft(qtyLeft);
                }

                for(TestStockSpentEntity spent:spentEntities)
                testStockSpentDAO.deleteById(spent.getId());
            }
        }catch (Exception e){
            LOGGER.info(e.toString());
        }

    }
}
