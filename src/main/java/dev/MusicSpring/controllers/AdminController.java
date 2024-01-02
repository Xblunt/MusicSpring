package dev.MusicSpring.controllers;


import dev.MusicSpring.db.dto.ShortUser;
import dev.MusicSpring.db.entities.auth.AuthUserEntity;
import dev.MusicSpring.db.repositories.AuthUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin")
public class AdminController {
    @Autowired
    private AuthUserRepo authUserRepo;

//    @GetMapping("users")
//    public Set<AuthUserEntity> getAllUsers(){
//
//        return authUserRepo.findAll();
//    }
//@GetMapping("/users")
//public Page<AuthUserEntity> geAllStudents(
//        @RequestParam(defaultValue = "0") int page,
//        @RequestParam(defaultValue = "2") int size) {
//
//    PageRequest pageRequest = PageRequest.of(page, size);
//    return authUserRepo.findAll(pageRequest);
//}
//@GetMapping("/users")
//public Page<AuthUserEntity> geAllStudents(
//        @RequestParam(defaultValue = "0") int page,
//        @RequestParam(defaultValue = "2") int size) {
//    PageRequest pageRequest = PageRequest.of(page, size);
//    return authUserRepo.findAll(pageRequest);
//}

    @GetMapping("/users")
    public Page<ShortUser> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size)
//                @RequestParam(defaultValue = "user_id") String sortColumn,
//                @RequestParam(defaultValue = "asc") String sortDirection)
    {

//            PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.valueOf(sortDirection.toUpperCase()),sortColumn));
        PageRequest pageRequest = PageRequest.of(page, size);
        return authUserRepo.findAll(pageRequest)
                .map(el -> new ShortUser(el.getRoleId(),el.getUsername(), el.getName(), el.getSurname()));
    }


//    @PostMapping(value="users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public AuthUserEntity createStudent(@RequestBody AuthUserEntity user){
//        return authUserRepo.save(user);
//    }s

    @PostMapping(value = "/users", consumes = "application/json", produces = "application/json")
//          value = "/students", produces = "application/json")
    public AuthUserEntity createStudent(@RequestBody AuthUserEntity user){
        authUserRepo.save(user);
        return user;
    }
//    @PutMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
//    public AuthUserEntity changeStudent(@RequestBody AuthUserEntity changingStudent){
//        return authUserRepo.save(changingStudent);
//    }
@PutMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
public AuthUserEntity changeStudent(@PathVariable("id") Long id, @RequestBody AuthUserEntity updatedStudent) {
    AuthUserEntity existingStudent = authUserRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Студент не найден: " + id));


    existingStudent.setName(updatedStudent.getName());
    existingStudent.setSurname(updatedStudent.getSurname());
    existingStudent.setUsername(updatedStudent.getUsername());


    return authUserRepo.save(existingStudent);
}
//    @DeleteMapping(value = "users/{user_id}")
//    public int deleteStudent(@PathVariable("user_id")Long user_id){
//        return authUserRepo.deleteUser(user_id);
//    }
//    @DeleteMapping (value = "users/{user_id}")//, consumes = "application/json", produces = "application/json")
//    public Long deleteUser(@PathVariable("user_id")Long user_id)
//    {
////        return  removeStudent(id);
//        authUserRepo.deleteById(user_id);
//        return user_id;
//   }


    @DeleteMapping(value = "users/{user_id}")
    public Long deleteUser(@PathVariable("user_id") Long userId) {
        authUserRepo.deleteById(userId);
        return userId;
    }

//    @Transactional
//    public void deleteUserAndAddress(Long user_id) {
//        Optional<ShortUser> userOptional = AuthUserRepo.findById(user_id);
//        userOptional.ifPresent(user -> {
//            Address address = user.getAddress();
//            if (userId != null) {
//                user.setAddress(null); // Удаление связи между пользователем и адресом
//                address.setUser(null); // Удаление связи между адресом и пользователем
//                AuthUserEntity.delete(user_id); // Удаление адреса
//            }
//            RoleUserEntity.delete(user_id); // Удаление пользователя
//        });
//    }
}