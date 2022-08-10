package in.jamuna.hms.services.hospital;

import in.jamuna.hms.config.GlobalValues;
import in.jamuna.hms.dao.hospital.billing.ProceduresDAO;
import in.jamuna.hms.dao.hospital.stock.*;
import in.jamuna.hms.dto.reports.MiniTestStockDTO;
import in.jamuna.hms.dto.common.CommonIdAndNameDto;
import in.jamuna.hms.dto.common.CommonWithDouble;
import in.jamuna.hms.dto.reports.PerTestProductStockInfo;
import in.jamuna.hms.dto.reports.TestProductMapping;
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

    public List<CommonWithDouble> getSpentStockByTypeAndDate(String type, Date date) {
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

            List<CommonWithDouble> dto = new ArrayList<>();

            List<TestProductEntity> productEntities = testProductDAO.findAll();
            for( TestProductEntity prod:productEntities ){
                CommonWithDouble item = new CommonWithDouble();
                item.setId(prod.getId());
                item.setName(prod.getName());

                double sum = list.stream().
                        filter( s -> testProductDAO.findBySpentStock(s)==prod.getId() )
                        .map(m -> m.getQty()).reduce(0.0,(a,b)->a+b);

                item.setNo(sum);
                dto.add(item);
            }
            return dto;
        }catch (Exception e){
            LOGGER.info(e.getMessage());
        }
        return new ArrayList<>();
    }

    public List<PerTestProductStockInfo> getStockSummaryByPageAndTypeAndDate(Integer no, int perPage, Date startDate, Date endDate) {
        //get product id, name, company
        //get opening & closing stock with you & same for allocated
        //get expired stock whre qtyleft > 0
        //get mappings and tests completed in each
        //show total spent
        List<PerTestProductStockInfo> list = new ArrayList<>();
        try{
            List<TestProductEntity> products = testProductDAO.getByPage(no,perPage);

            for(TestProductEntity product:products){
                PerTestProductStockInfo dto = new PerTestProductStockInfo();
                dto.setId(product.getId());
                dto.setProduct(product.getName());


                //closing stock means total stock of that product at the end of that date excluding expired stock
                //does stock expiry tis month count in stock
                Date thisMonthFirstDate = new DateTime(endDate).withDayOfMonth(1).toDate();
                double closingStock = testStockDAO.findTotalQtyLeftByProductAndExcludingExpired(product,thisMonthFirstDate )
                        .stream().map(t->t.getQtyLeft()).reduce(0.0,(a,b)->a+b);
                dto.setClosingStock( Math.round(closingStock* 1000)/1000.0 );

                //opening stock = closingstock + allocated - expired;
                double allocatedStock = allocateStockDAO.findByQtyLeftAndStockProd(0.0, product)
                        .stream().map(m->m.getQtyLeft()).reduce(0.0, (a, b) -> a+ b);
                Date nextMonthFirstDate = new DateTime(startDate).plusMonths(1).withDayOfMonth(1).toDate();

                //allocatedopen means allocatedclose - spent
                dto.setAllocatedClosing(Math.round(allocatedStock* 1000)/1000.0);


                List<TestStockEntity> expiredBatchesInBetween = new ArrayList<>();
                if(nextMonthFirstDate.before(thisMonthFirstDate) || nextMonthFirstDate.equals(thisMonthFirstDate)){
                    expiredBatchesInBetween = testStockDAO.findByProductAndExpiry(product,nextMonthFirstDate,thisMonthFirstDate);
                }
                double expiredSum = 0;
                expiredSum=expiredBatchesInBetween.stream().map(m->m.getQtyLeft()).reduce(0.0,(a,b)->a+b);
                dto.setOpeningStock( closingStock+allocatedStock-expiredSum );
                dto.setOpeningStock(Math.round(dto.getOpeningStock()*1000)/1000.0);

                List<TestStockSpentEntity> spents = testStockSpentDAO.findByStartAndEndDateAndProduct(startDate,endDate,product);

                dto.setAllocatedOpening( allocatedStock - spents.stream().map( s -> s.getQty() ).reduce(0.0,(a,b)->a+b) );
                dto.setAllocatedOpening( Math.round(dto.getAllocatedOpening()*1000)/1000.0 );

                List<TestProductMapping> mappings = new ArrayList<>();

                for( ProcedureProductMappingEntity map:product.getMappings()){
                    TestProductMapping m = new TestProductMapping();
                    ProcedureRatesEntity procedureRates = procedureProductDAO.findTestById(map.getId());
                    m.setId(map.getId());
                    m.setName( procedureRates.getProcedure() );
                    //numbr of tests completed from bills
                    long completedTests = proceduresDAO.countByStartAndEndDateAndProcedure(startDate,endDate,procedureRates);
                    m.setNo1(completedTests);
                    long completedTestsBySpent = spents.stream().
                            filter( spent -> spent.getItem().getProcedure().getId()==procedureRates.getId() ).
                            map( spent -> spent.getQty() ).count();
                    double totalRatio = spents.stream().
                            filter( spent -> spent.getItem().getProcedure().getId()==procedureRates.getId() ).
                            map( spent -> 1.0/spent.getQty() ).reduce(0.0,(a,b)-> a+b);

                    long avgRatio = Math.round( totalRatio/completedTestsBySpent );

                    m.setNo2(completedTestsBySpent);
                    m.setNo3(avgRatio);

                    double totalSpent = spents.stream().
                            filter( spent -> spent.getItem().getProcedure().getId()==map.getTest().getId() ).
                            map( spent -> spent.getQty() ).reduce(0.0,(a,b)-> a+b);
                    m.setNo4(totalSpent);
                    m.setNo4( Math.round(m.getNo4()*1000)/1000.0 );

                    mappings.add(m);

                }
                dto.setMapping(mappings);

                list.add(dto);
            }

        }catch (Exception e){
            LOGGER.info(e.getMessage());
        }

        return list;
    }
}
