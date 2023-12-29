package com.example.beecoders_test.Service;


import com.example.beecoders_test.Entities.Course;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface ICourseService {

    List<Course> retrieveAllCourse();
    Course updateCourse (Course ce);
    Course addCourse(Course ce) throws IOException;
    Course retrieveCourse (Integer idContrat);
    void removeCourse(Integer idContrat) ;

}
