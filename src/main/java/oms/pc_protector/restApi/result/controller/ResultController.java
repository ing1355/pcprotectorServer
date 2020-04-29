package oms.pc_protector.restApi.result.controller;

import oms.pc_protector.apiConfig.model.SingleResult;
import oms.pc_protector.apiConfig.service.ResponseService;
import oms.pc_protector.restApi.result.service.ResultService;
import oms.pc_protector.restApi.user.model.UserRequestVO;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Log4j2
@CrossOrigin
@RestController
@RequestMapping("/v1/results")  // 진단상세조회 API
public class ResultController {

    private final ResponseService responseService;
    private final ResultService resultService;

    public ResultController(ResponseService responseService, ResultService resultService) {
        this.responseService = responseService;
        this.resultService = resultService;
    }


    // 모든 점검결과를 가져온다.
    @GetMapping(value = "")
    public SingleResult<?> findAllResult() {
        HashMap<String, Object> map = new HashMap<>();
        List<?> list = resultService.findAllResult();
        map.put("results", list);
        return responseService.getSingleResult(map);
    }


    @GetMapping(value = "/checked")
    public SingleResult<?> findAllChecked() {
        HashMap<String, Object> map = new HashMap<>();
        List<?> list = resultService.findAllCheckedResult();
        map.put("results", list);
        return responseService.getSingleResult(map);
    }


    @GetMapping(value = "/unchecked")
    public SingleResult<?> findAllUnChecked() {
        HashMap<String, Object> map = new HashMap<>();
        List<?> list = resultService.findAllUnCheckedResult();
        map.put("results", list);
        return responseService.getSingleResult(map);
    }


    // 해당 점검결과의 세부사항을 가져온다.
    @GetMapping(value = "/details")
    public SingleResult<?> findResultsDetailsByUserName(
            @RequestParam (value = "ip") String ipAddress,
            @RequestParam (value = "checkTime") String checkTime) {
        HashMap<String, Object> map = new HashMap<>();
        HashMap itemMap = Optional
                .ofNullable(resultService.findDetailsWithProcessListByUserId(ipAddress, checkTime))
                .orElseThrow(() -> new RuntimeException("파라미터를 확인해주세요."));
        map.put("itemDetails", itemMap);
        return responseService.getSingleResult(map);
    }


    // 조건 검색하여 점검결과를 가져온다.
     @GetMapping(value = "/search")
     public SingleResult<?> findByUserIdWithIpAddress(
     @RequestParam(value = "id", required = true) String id,
     @RequestParam(value = "ip", required = false) String ipAddress,
     @RequestParam(value = "name", required = false) String name,
     @RequestParam(value = "startDay", required = false) String startDay,
     @RequestParam(value = "endDay", required = false) String endDay) {
     log.info("id : " + id);
     log.info("name : " + name);
     log.info("ipAddress : " + ipAddress);
     log.info("startDay : " + startDay);
     log.info("endDay : " + endDay);
     HashMap<String, Object> map = new HashMap<>();
     UserRequestVO userRequestVO = new UserRequestVO();
     Optional.ofNullable(id).ifPresent(userRequestVO::setUserId);
     Optional.ofNullable(ipAddress).ifPresent(userRequestVO::setIpAddress);
     Optional.ofNullable(startDay).ifPresent(userRequestVO::setStartDay);
     Optional.ofNullable(endDay).ifPresent(userRequestVO::setEndDay);
     List<?> list = resultService.findByUserIdWithIpAddress(userRequestVO);
     map.put("results", list);
     return responseService.getSingleResult(map);
     }


}
