package in.jamuna.hms.controllers.hospital.manager.stock;

import in.jamuna.hms.config.GlobalValues;
import in.jamuna.hms.dto.common.CommonIdAndNameDto;
import in.jamuna.hms.dto.common.InfoOfPage;
import in.jamuna.hms.services.hospital.TestStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("manager/stock")
public class TestStockController {

    @Autowired
    TestStockService testStockService;

    private static final Logger LOGGER = Logger.getLogger(TestStockController.class.getName());

    private static final String PAGE = "TestStock";

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
    public String addBillGroup(@PathVariable("type") String type,@RequestParam(name="name") String name, Model model) {

        try {
            testStockService.addByType(type,name);
        }catch(Exception e) {
            LOGGER.info(e.getMessage());
        }
        model.addAttribute("type",type);
        return "redirect:/manager/stock/"+type+"-page/";
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
    public @ResponseBody List<CommonIdAndNameDto> getCommonByName(@PathVariable("type") String type,@RequestParam("term") String term){
        try{
            return testStockService.getCommonByName(term,type);
        }
        catch (Exception e){
            LOGGER.info(e.toString());
        }
        return new ArrayList<>();
    }

}
