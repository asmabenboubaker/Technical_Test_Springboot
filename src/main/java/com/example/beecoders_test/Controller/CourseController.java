package com.example.beecoders_test.Controller;


import com.example.beecoders_test.Entities.Course;
import com.example.beecoders_test.Service.ICourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
//@CrossOrigin(origins = "*")
public class CourseController {


    private final ICourseService ICourseService;
    @GetMapping("/all")
    @ResponseBody
    public List<Course> getCourse(){
        return ICourseService.retrieveAllCourse();
    }
    @PostMapping("/add")
    public Course addCourse(@RequestBody Course course){
        return ICourseService.addCourse(course);
    }
    @PutMapping ("/update")
    public Course updateCourse(@RequestBody Course course){
        return ICourseService.updateCourse(course);
    }
    @GetMapping("/get/{id-course}")
    public Course getById(@PathVariable("id-course") Integer id){
        return ICourseService.retrieveCourse(id);
    }
    @DeleteMapping("/remove/{id-course}")
    public void removeCourse(@PathVariable("id-course")Integer id){
        ICourseService.removeCourse(id);
    }
}
