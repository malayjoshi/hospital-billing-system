package in.jamuna.hms.controllers.hospital.common;

import in.jamuna.hms.services.hospital.TestStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/common/stock")
public class CommonTestStockController {
    @Autowired
    TestStockService testStockService;

    @RequestMapping("/allocated-stock-page")
    public String allocatedStockPage(Model model){
        model.addAttribute("list",testStockService.getAllocatedStockOrderByProduct());
        return "/Common/AllocatedStock";
    }
}
