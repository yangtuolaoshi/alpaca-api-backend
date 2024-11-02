package icu.ytlsnb.alpacaapibackend.controller;

import icu.ytlsnb.alpacaapibackend.annotation.AdminRequire;
import icu.ytlsnb.alpacaapibackend.exception.BusinessException;
import icu.ytlsnb.alpacaapibackend.model.PageResult;
import icu.ytlsnb.alpacaapibackend.model.Result;
import icu.ytlsnb.alpacaapibackend.model.pojo.InterfaceInfo;
import icu.ytlsnb.alpacaapibackend.model.request.OnlineInvokeRequest;
import icu.ytlsnb.alpacaapibackend.service.InterfaceInfoService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static icu.ytlsnb.alpacaapibackend.constant.ResultCodes.PARAMS_ERROR;

@RestController
@RequestMapping("/interfaceInfo")
public class InterfaceInfoController {
    @Resource
    private InterfaceInfoService interfaceInfoService;

    @GetMapping("/page")
    public PageResult<List<InterfaceInfo>> getInterfaceInfos(int page, int size) {
        if (page < 0) {
            page = 1;
        }
        if (size < 0) {
            size = 5;
        }
        return new PageResult<>(
                page,
                size,
                interfaceInfoService.getByPage(page, size),
                interfaceInfoService.getCount()
        );
    }

    @GetMapping("/{id}")
    public Result<InterfaceInfo> getById(@PathVariable Long id) {
        return Result.ok(interfaceInfoService.getById(id));
    }

    @PostMapping("/invoke")
    public Result<Object> onlineInvoke(@RequestBody OnlineInvokeRequest onlineInvokeRequest, HttpServletRequest request) {
        if (onlineInvokeRequest == null) {
            throw new BusinessException(PARAMS_ERROR, "请求为空...");
        }
        Long id = onlineInvokeRequest.getId();
        if (id == null || id < 0) {
            throw new BusinessException(PARAMS_ERROR, "接口ID为空...");
        }
        return Result.ok(interfaceInfoService.onlineInvoke(onlineInvokeRequest, request));
    }

    @AdminRequire
    @PostMapping
    public Result<Object> addInterfaceInfo(@RequestBody InterfaceInfo interfaceInfo, HttpServletRequest request) {
        interfaceInfoService.insert(interfaceInfo, request);
        return Result.ok();
    }

    @AdminRequire
    @PutMapping
    public Result<Object> updateInterfaceInfo(@RequestBody InterfaceInfo interfaceInfo) {
        interfaceInfoService.update(interfaceInfo);
        return Result.ok();
    }

    @AdminRequire
    @DeleteMapping("/{id}")
    public Result<Object> deleteInterfaceInfo(@PathVariable("id") Long id) {
        interfaceInfoService.deleteById(id);
        return Result.ok();
    }

    @AdminRequire
    @PutMapping("/publish/{id}")
    public Result<Object> publishInterfaceInfo(@PathVariable Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException(PARAMS_ERROR, "请求参数错误...");
        }
        interfaceInfoService.publishInterfaceInfo(id);
        return Result.ok();
    }

    @AdminRequire
    @PutMapping("/offline/{id}")
    public Result<Object> offlineInterfaceInfo(@PathVariable Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException(PARAMS_ERROR, "请求参数错误...");
        }
        interfaceInfoService.offlineInterfaceInfo(id);
        return Result.ok();
    }
}
