package dev.vorstu.controllers;


import dev.vorstu.db.dto.ShortUser;
import dev.vorstu.db.entities.auth.BaseRole;
import dev.vorstu.db.repositories.AuthUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("api/home")
public class HomeController {
    @Autowired
    private AuthUserRepo authUserRepo;

//    @GetMapping("users")
//    public Set<ShortUser> getAllUsers() {
//        return authUserRepo.findAll().stream()
//                .filter(el -> el.getRoles().stream().anyMatch(role ->
//                        !role.getRole().equals(BaseRole.SUPER_USER)))
//                .map(el -> new ShortUser(el.getId(), el.getName(), el.getSurname()))
//                .collect(Collectors.toSet());
//   }
//@GetMapping("/users")
//public Page<ShortUser> getAllUsers(
//        @RequestParam(defaultValue = "0") int page,
//        @RequestParam(defaultValue = "2") int size) {
//    PageRequest pageRequest = PageRequest.of(page, size);
//    return authUserRepo.findAll(pageRequest)
//            .filter(el -> el.getRoles().stream()
//                    .anyMatch(role -> !role.getRole().equals(BaseRole.SUPER_USER)))
//            .map(el -> new ShortUser(el.getId(), el.getName(), el.getSurname()));
//}
//@GetMapping("/users")
//public Page<ShortUser> getAllUsers(
//        @RequestParam(defaultValue = "0") int page,
//        @RequestParam(defaultValue = "2") int size) {
//    PageRequest pageRequest = PageRequest.of(page, size);
//    List<ShortUser> users = StreamSupport.stream(authUserRepo.findAll(pageRequest).spliterator(), false)
//            .filter(el -> el.getRoles().stream()
//
//                    .anyMatch(role -> !role.getRole().equals(BaseRole.SUPER_USER)))
//            .filter(el -> !el.getUsername().equals("admin"))
//            .map(el -> new ShortUser(el.getRoleId(), el.getUsername(), el.getName(), el.getSurname()))
//            .collect(Collectors.toList());
//    return PageableExecutionUtils.getPage(users, pageRequest, () -> authUserRepo.count());
//}
@GetMapping("/users")
public Page<ShortUser> getAllUsers(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "2") int size) {
    PageRequest pageRequest = PageRequest.of(page, size);
    List<ShortUser> users = StreamSupport.stream(authUserRepo.findAll(pageRequest).spliterator(), false)
            .filter(el -> !el.getRoles().stream()
                    .allMatch(role -> !role.getRole().equals(BaseRole.STUDENT)))
            .map(el -> new ShortUser(el.getRoleId(), el.getUsername(), el.getName(), el.getSurname()))
            .collect(Collectors.toList());


    return new PageImpl<>(users, pageRequest, authUserRepo.count());
//    return new PageImpl<>(users, pageRequest, countStudents() );
}

//    private long countStudents() {
//        return authUserRepo.countByRole(BaseRole.STUDENT);
//    }
}