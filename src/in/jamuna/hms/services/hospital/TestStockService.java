package in.jamuna.hms.services.hospital;

import in.jamuna.hms.config.GlobalValues;
import in.jamuna.hms.dao.ProcedureProductDAO;
import in.jamuna.hms.dao.TestProductDAO;
import in.jamuna.hms.dao.hospital.ProceduresDAO;
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
    private ProceduresDAO proceduresDAO;

    @Autowired
    private TestCompanyDAO testCompanyDAO;

    @Autowired
    private ConverterService converterService;

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
        }else if(type.equals("product")){
            if(action.equals("enable"))
                testProductDAO.toggle(id,true);
            else if(action.equals("disable"))
                testProductDAO.toggle(id,false);
        }
    }

    public List<CommonIdAndNameDto> getCommonByName(String term, String type) {
        if(type.equals("company")){

        }else if (type.equals("product")){
            return testProductDAO.findByNameAndLimit(term, GlobalValues.getSearchlimit()).stream().map(s->
                    new CommonIdAndNameDto(s.getId(),s.getName(),s.isEnabled())
            ).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public void addProduct(String name, Integer id) {
        testProductDAO.add(name, testCompanyDAO.findById(id) );
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
}
