package com.example.beecoders_test.Service;


import com.example.beecoders_test.Entities.Course;
import com.example.beecoders_test.Repository.ICourseRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService implements ICourseService {
    @Autowired
    private ICourseRepo iCourseRepo;

    public List<Course> retrieveAllCourse() {
        List<Course> courses = (List<Course>) iCourseRepo.findAll();

        // Update each Course entity to include the image URL
        courses.forEach(course -> {
            course.setImageUrl(getImageUrl(course.getImage()));
        });

        return courses;
    }

    private String getImageUrl(String imageName) {
        // You need to construct the URL based on the location where your images are served
        // Example: Assuming images are served from "/uploads" endpoint
        return "http://localhost:8081/uploads/" + imageName;
    }

    @Override
    public Course updateCourse(Course ce) {
        return iCourseRepo.save(ce);
    }

    @Override
    public Course addCourse(Course ce) {
       // ce.setImage(imageFile.getBytes());
        return iCourseRepo.save(ce);
    }

    @Override
    public Course retrieveCourse(Integer idCourse) {
        return iCourseRepo.findById(idCourse).get();
    }

    @Override
    public void removeCourse(Integer idCourse) {
     iCourseRepo.deleteById(idCourse);
    }
}
