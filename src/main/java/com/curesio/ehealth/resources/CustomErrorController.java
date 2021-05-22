package com.curesio.ehealth.resources;

import com.curesio.ehealth.exceptions.ResourceNotFoundException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/")
public class CustomErrorController extends ExceptionHandlingController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) throws Exception {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                throw new ResourceNotFoundException("The requested resource does not exist!");
            } else {
                throw new Exception(request.toString());
            }
        }

        return "error";
    }
}
