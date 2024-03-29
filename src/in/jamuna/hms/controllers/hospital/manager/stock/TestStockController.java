package in.jamuna.hms.controllers.hospital.manager.stock;

import in.jamuna.hms.config.GlobalValues;
import in.jamuna.hms.dto.cart.CartItemDTO;
import in.jamuna.hms.dto.common.CommonIdAndNameDto;
import in.jamuna.hms.dto.common.GenericMessage;
import in.jamuna.hms.dto.common.InfoOfPage;
import in.jamuna.hms.dto.common.ProductForInvoice;
import in.jamuna.hms.services.hospital.BillingService;
import in.jamuna.hms.services.hospital.LabService;
import in.jamuna.hms.services.hospital.TestStockService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Controller
@RequestMapping("manager/stock")
public class TestStockController {
    @Autowired
    ModelMapper mapper;
    @Autowired
    TestStockService testStockService;

    @Autowired
    LabService labService;
    @Autowired
    BillingService billingService;

    private static final Logger LOGGER = Logger.getLogger(TestStockController.class.getName());

    private static final String PAGE = "TestStock";

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }

    private static final String MAPPING_PAGE="/Manager/Billing/ProcedureProduct";

    @RequestMapping({"/{type}-page/{no}","/{type}-page"})
    public String stockPage(@PathVariable(name = "type") String type, @PathVariable(name = "no",required = false ) Integer no, Model model){
        try {
            if(no == null)
                no=1;
            InfoOfPage info=new InfoOfPage();

            info.setCurrentPage(no);
            info.setPerPage(GlobalValues.getPerpage());
            info.setTotalPages( (int)Math.ceil(testStockService.getTotalCountByType(type)*1.0/GlobalValues.getPerpage() ) );

            model.addAttribute("list",testStockService.getListByPageAndType(no,type,GlobalValues.getPerpage()));
            model.addAttribute("info",info);
            model.addAttribute("type",type);
        }catch (Exception e){
            LOGGER.info(e.toString());
        }

        return "/Manager/Stock/Common";
    }

    @PostMapping("/add-{type}")
    public String addByType(@PathVariable("type") String type, @RequestParam(name="name") String name,
                             Model model) {

        try {
                testStockService.addByType(type,name);
            }
        catch(Exception e) {
            LOGGER.info(e.getMessage());
        }
        model.addAttribute("type",type);
        return "redirect:/manager/stock/"+type+"-page/";
    }



    @PostMapping("/add-test-product")
    public String addProduct(@RequestParam(name="name") String name,@RequestParam(name="id") Integer id ,Model model) {

        try {
            testStockService.addProduct(name,id);
        }catch(Exception e) {
            LOGGER.info(e.getMessage());
        }
        model.addAttribute("type","product");
        return "redirect:/manager/stock/"+"product"+"-page/";
    }

    @PostMapping("/add-procedure-product-mapping")
    public String addProductProcedureMapping(@RequestParam(name="productId") Integer productId
            ,@RequestParam(name="procedureId") Integer procedureId,@RequestParam("ratio") int ratio) {

        try {
            testStockService.addMapping(productId,procedureId,ratio);
        }catch(Exception e) {
            LOGGER.info(e.getMessage());
        }

        return "redirect:procedure/"+procedureId+"/stock-mapping";
    }

    @GetMapping("/{type}/{no}/{id}/{action}")
    public String toggle(@PathVariable("type") String type,@PathVariable(name="no") Integer no,
            @PathVariable("id")Integer id,@PathVariable("action") String action, Model model) {

        try {
            testStockService.toggleByType(type,id,action);
        }catch(Exception e) {
            LOGGER.info(e.getMessage());
        }
        model.addAttribute("type",type);
        return "redirect:/manager/stock/"+type+"-page/"+no;
    }

    @GetMapping("/{type}")
    public @ResponseBody List<ProductForInvoice> getCommonByName(@PathVariable("type") String type,
                                                                 @RequestParam("term") String term){
        try{
            if(type.equals("product")){
                return testStockService.getProductsByTerm(term);
            }
            else
                return testStockService.getCommonByName(term,type).stream().map( m -> mapper.map(m,ProductForInvoice.class) ).collect(Collectors.toList());
        }
        catch (Exception e){
            LOGGER.info(e.toString());
        }
        return new ArrayList<>();
    }

    @RequestMapping("procedure/{id}/stock-mapping")
    public String mappingPage(@PathVariable int id, Model model){
        try {
            model.addAttribute("test",labService.getTestByTestId(id));
            model.addAttribute("list",billingService.getAllProductMappingsByProcedureId(id));
        }catch (Exception e){
            LOGGER.info(e.toString());
        }
        return MAPPING_PAGE;
    }

    @RequestMapping("{procId}/mapping/change-ratio/{id}")
    public String changeMappingRatio(@PathVariable int id,
                                     @RequestParam("ratio") int ratio, @PathVariable int procId){
        try {
            if(ratio > 0) {
                testStockService.changeRatio(id, ratio);
            }
        }catch (Exception e){
            LOGGER.info(e.getMessage());
        }
        return "redirect:/manager/stock/procedure/"+procId+"/stock-mapping";
    }


    @RequestMapping("{procId}/mapping/delete/{id}")
    public String changeMappingRatio(@PathVariable int procId,@PathVariable int id
                                     ){
        try {
                testStockService.deleteByType(id, "stock-mapping");

        }catch (Exception e){
            LOGGER.info(e.getMessage());
        }
        return "redirect:/manager/stock/procedure/"+procId+"/stock-mapping";
    }

    @RequestMapping("/add-invoice-page")
    public String addInvoicePage(){
        return "/Manager/Stock/AddInvoice";
    }

    @RequestMapping("add-invoice-stock")
    public String addStcok(HttpServletRequest request,Model model){
        try{

            model.addAttribute("added",testStockService.addInvoice(request));
        }catch (Exception e){
            LOGGER.info(e.toString());
        }
        return "/Manager/Stock/AddInvoice";
    }

    @RequestMapping("allocate-stock-page")
    public String allocateStockPage(){

        return "/Manager/Stock/AllocateStock";
    }

    @RequestMapping("/get-batches-allocate")
    public  String getBatchesForAllocate(@RequestParam("product") int product,@RequestParam("qty") int qty,Model model){
        try{
            model.addAttribute("list",testStockService.getBatchesForAllocate(product,qty));
            model.addAttribute("qty",qty);
            model.addAttribute("id",product);
        }catch (Exception e){
            LOGGER.info(e.toString());
        }
        return "/Manager/Stock/AllocateStock";
    }

    @RequestMapping("/allocate-stock")
    public String allocateStock(@RequestParam int id,@RequestParam double qty,Model model){
        try {
            model.addAttribute("allocated",testStockService.allocateStock(id,qty));
        }catch (Exception e){
            LOGGER.info(e.toString());
        }

        return "/Manager/Stock/AllocateStock";
    }

    @RequestMapping("{id}/view-stock")
    public String viewStockPage(@PathVariable int id,Model model){
        try {
            model.addAttribute("list", testStockService.viewStockByProduct(id) );
        }catch (Exception e){
            LOGGER.info(e.getMessage());
        }
        return "/Manager/Stock/TestStock";
    }

    @RequestMapping("/spent-stock-page")
    public String spentStockPage(Model model){
        model.addAttribute("type",GlobalValues.getSummaryType());
        return "/Manager/Stock/SpentStock";
    }

    @RequestMapping("/get-spent-stock")
    public String getSpentStock(@RequestParam("type") String type, @RequestParam("date") Date date, Model model){
        try{
            model.addAttribute("list",
                    testStockService.getSpentStockByTypeAndDate(type,date));
        }catch (Exception e){
            LOGGER.info(e.getMessage());
        }
        model.addAttribute("type",GlobalValues.getSummaryType());
        return "/Manager/Stock/SpentStock";
    }


    @RequestMapping({"stock-summary-page","stock-summary-page/{no}"})
    public String getTotalStockByProduct(
                            @PathVariable(name = "no",required = false ) Integer no,
                            Model model){
        try {
                if(no == null)
                    no=1;
                InfoOfPage info=new InfoOfPage();

                info.setCurrentPage(no);
                info.setPerPage(GlobalValues.getPerpage());
                info.setTotalPages( (int)Math.ceil(testStockService.getTotalCountByType("product")*1.0/GlobalValues.getPerpage() ) );

                model.addAttribute("list",
                        testStockService.getStockSummaryByPage(no,GlobalValues.getPerpage()));
                model.addAttribute("info",info);


        }catch (Exception e){
            LOGGER.info(e.toString());
        }

        return "/Manager/Stock/StockSummary";
    }


    @RequestMapping("set-company")
    public @ResponseBody GenericMessage setCompany(@RequestParam int id,@RequestParam int item){
        testStockService.setCompany(id,item);
        GenericMessage m =  new GenericMessage();
        m.setMessage("ok");
        return m;
    }



    @RequestMapping("allocated-stock/truncate-left")
    public @ResponseBody GenericMessage truncateAllocatedLeft(@RequestParam int id){
        testStockService.truncateProductsAllocatedLeft(id);
        GenericMessage m =  new GenericMessage();
        m.setMessage("ok");
        return m;
    }


    @RequestMapping("/revert-transaction-page")
    public String revertTransactionPage(){
        return "/Manager/Stock/RevertTrans";
    }


    @RequestMapping("/get-spent-stock-by-tid")
    public String getSpentByTid(@RequestParam int tid,Model model){

        model.addAttribute("list",testStockService.findBillByTidAndSpent(tid));
        model.addAttribute("tid",tid);

        return "/Manager/Stock/RevertTrans";
    }

    @RequestMapping({"/revert-transaction/{tid}","/revert-transaction/{tid}/{id}"})
    public String revertTransaction( @PathVariable int tid, @PathVariable(required = false) Integer id,
                                      Model model){
        try {

            testStockService.revertTransactionByType(tid,id);

        }catch (Exception e){
            LOGGER.info(e.getMessage());
        }
        return "redirect:/manager/stock/get-spent-stock-by-tid?tid="+tid;
    }

}
