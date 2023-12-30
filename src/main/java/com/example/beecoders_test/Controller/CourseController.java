package com.example.beecoders_test.Controller;


import com.example.beecoders_test.Entities.Course;
import com.example.beecoders_test.Service.ICourseService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

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
    /*@PostMapping(value = "/add", consumes = "multipart/form-data")
    public Course addCourse(
            @RequestParam("imageFile") MultipartFile imageFile,
            @RequestParam("name") String name,
            @RequestParam("price") float price,
            @RequestParam("description") String description
    ) throws IOException {
        // Your implementation here, for example, delegate to the service
        Course course = new Course();
        course.setName(name);
        course.setPrice(price);
        course.setDescription(description);
        course.setImage(imageFile.getBytes()); // Assuming your Course entity has a byte[] field for the image

        return ICourseService.addCourse(course);
    }*/
    @PostMapping(value = "/add", consumes = "multipart/form-data")
    public ResponseEntity<Course> addCourseWithImage(
            @RequestParam("imageFile") MultipartFile imageFile,
            @RequestParam("name") String name,
            @RequestParam("price") float price,
            @RequestParam("description") String description) throws IOException {

        try {
            Course course = new Course();
            course.setName(name);
            course.setPrice(price);
            course.setDescription(description);

            // Save image to a directory or a database, update the Course entity accordingly
            String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
            //Path targetLocation = Paths.get("uploads").resolve(fileName);

            Path uploadDirectory = Paths.get("uploads");

            if (!Files.exists(uploadDirectory)) {
                Files.createDirectories(uploadDirectory);
            }

            Path targetLocation = uploadDirectory.resolve(fileName);

            Files.copy(imageFile.getInputStream(), targetLocation);
            course.setImage(fileName); // Assuming Course has a field to store the image file name

            ICourseService.addCourse(course);

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create("/course/get/" + course.getIdCourse()));

            return new ResponseEntity<>(course, headers, HttpStatus.CREATED);
        } catch (Exception e) {
            // Log the exception details
            System.out.println("Exception during course addition:"+ e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

  /*  @PutMapping ("/update")
    public Course updateCourse(@RequestBody Course course){
        return ICourseService.updateCourse(course);
    }
*/
  @PutMapping("/update")
  public ResponseEntity<Course> updateCourseWithImage(
          @RequestParam("imageFile") MultipartFile imageFile,
          @RequestParam("idCourse") Integer idCourse,
          @RequestParam("name") String name,
          @RequestParam("price") float price,
          @RequestParam("description") String description) throws IOException {

      try {
          //convert idCourse to integer
          Course course = ICourseService.retrieveCourse(idCourse);
          if (course == null) {
              return new ResponseEntity<>(HttpStatus.NOT_FOUND);
          }

          // Update course details
          course.setName(name);
          course.setPrice(price);
          course.setDescription(description);

          // Save image to a directory or a database, update the Course entity accordingly
          String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
          Path uploadDirectory = Paths.get("uploads");

          if (!Files.exists(uploadDirectory)) {
              Files.createDirectories(uploadDirectory);
          }

          Path targetLocation = uploadDirectory.resolve(fileName);

          Files.copy(imageFile.getInputStream(), targetLocation);
          course.setImage(fileName); // Assuming Course has a field to store the image file name

          ICourseService.updateCourse(course);

          return new ResponseEntity<>(course, HttpStatus.OK);
      } catch (Exception e) {
          // Log the exception details
          System.out.println("Exception during course update:" + e);
          return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
  }


    @GetMapping("/get/{id-course}")
    public Course getById(@PathVariable("id-course") Integer id){
        return ICourseService.retrieveCourse(id);
    }
    @DeleteMapping("/remove/{id-course}")
    public void removeCourse(@PathVariable("id-course")Integer id){
        ICourseService.removeCourse(id);
    }

    @GetMapping("/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) throws IOException {
        Path imagePath = Paths.get("uploads").resolve(imageName);
        Resource resource = (Resource) new UrlResource(imagePath.toUri());


            return ResponseEntity.ok().body(resource);

    }

}
