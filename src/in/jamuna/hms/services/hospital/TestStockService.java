package in.jamuna.hms.services.hospital;

import in.jamuna.hms.config.GlobalValues;
import in.jamuna.hms.dao.hospital.TestCompanyDAO;
import in.jamuna.hms.dao.hospital.TestSupplierDAO;
import in.jamuna.hms.dto.common.CommonIdAndNameDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class TestStockService {
    @Autowired
    private TestSupplierDAO testSupplierDAO;

    @Autowired
    private TestCompanyDAO testCompanyDAO;

    @Autowired
    private ConverterService converterService;

    private static final Logger LOGGER = Logger.getLogger(TestStockService.class.getName());


    public long getTotalCountByType(String type) {
        long count = 0;
        if(type.equals("supplier")){
            count =  testSupplierDAO.getCount();
        }else if(type.equals("company")){
            count =  testCompanyDAO.getCount();
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
        }catch (Exception e){
            LOGGER.info(e.toString());
        }
            return null;

    }

    public void addByType(String type,String name ) {
        if(type.equals("supplier")){
            testSupplierDAO.add(name);
        }else if(type.equals("company")){
           testCompanyDAO.add(name);
        }
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
        }
    }

    public List<CommonIdAndNameDto> getCommonByName(String term, String type) {
        if(type.equals("company")){
            return testCompanyDAO.findByNameAndLimit(term, GlobalValues.getSearchlimit()).stream().map(s->
                    new CommonIdAndNameDto(s.getId(),s.getName(),s.isEnabled())
            ).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
