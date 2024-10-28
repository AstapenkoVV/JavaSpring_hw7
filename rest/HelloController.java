package ru.gb.timesheet.rest;

import org.springframework.web.bind.annotation.*;

//@Controller
//@ResponseBody
@RestController
public class HelloController {

    // @RequestMapping(method = RequestMethod.GET, value = "/hello")
    // GET http://localhost:8080/hello?userName=Igor
    // GET http://localhost:8080/hello?userName=Igor&city=Moscow&date=2024-10-11
    @GetMapping("/hello")
    public String helloPage(@RequestParam(required = false) String userName) {
        return "<h1>Hello, " + userName + "!</h1>";
    }

    // GET http://localhost:8080/hello/Igor
    // GET http://localhost:8080/hello/Alex
    @GetMapping("/hello/{username}")
    public String helloPagePathVariable(@PathVariable(required = false) String username) {
        return "<h1>Hello, " + username + "!</h1>";
    }

//    public String helloPage(@RequestParam("userName") String un) {
//   return "<h1>Hello, " + un + "!</h1>";
//}
    // GET http://localhost:8080/home
    @GetMapping("/home")
    public String homePage() {
        return "Home page";
    }
}
