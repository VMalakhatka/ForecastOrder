//package org.example.service;
//
//import org.example.dao.user.UserDao;
//import org.example.dto.auth.UserRegistrationFormData;
//import org.example.dto.order.OrderResponseDto;
//import org.example.dto.user.ExtendedUserResponseDto;
//import org.example.dto.user.UserCreateRequestDto;
//import org.example.dto.user.UserResponseDto;
//import org.example.entity.user.User;
//import org.example.mapper.GoodsMapper;
//import org.example.mapper.OrderMapper;
//import org.example.mapper.UserMapper;
//import org.hibernate.Hibernate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class UserService {
//    private final UserDao userDao;
//
//    @Autowired
//    public UserService(UserDao userDao) {
//        this.userDao = userDao;
//    }
//
//    public List<UserResponseDto> getUsers(String lastNameStartsWith) {
//        return userDao.readAll().stream()
//                .filter(u -> lastNameStartsWith.isBlank() || u.getLastName().startsWith(lastNameStartsWith))
//                .map(userMapper::toUserResponseDto)
//                .toList();
//    }
//
//    public UserResponseDto getUser(long id) {
//        return userMapper.toUserResponseDto(userDao.read(id));
//    }
//
//    @Transactional // требуется, потому что есть дозагрузка ленивой части
//    public ExtendedUserResponseDto getExtendedUser(long id) {
//        User user = userDao.read(id);
//        Hibernate.initialize(user.getOrders()); // помогает дозагрузить то, что проинициализировано лениво
//        List<OrderResponseDto> orders = user.getOrders().stream()
//                .map(o -> orderMapper.toOrderGetResponseDto(o, goodsMapper.toGoodsItemDtoList(o.getItems())))
//                .toList();
//        return userMapper.toExtendedUserResponseDto(userDao.read(id), orders);
//    }
//
//    @Transactional
//    public UserResponseDto updateUser(long id, UserCreateRequestDto userResponseDto) {
//        return userMapper.toUserResponseDto(userDao.update(userMapper.toUser(userResponseDto), id));
//    }
//
//    @Transactional
//    public UserResponseDto createUser(UserCreateRequestDto candidate) {
//        Optional<User> user = userDao.findByCredentials(candidate.firstName(), candidate.lastName(), candidate.email());
//        if (user.isPresent())
//            throw new IllegalStateException("Пользователь с указанными фамилией, именем и email уже существует");
//        return userMapper.toUserResponseDto(userDao.create(userMapper.toUser(candidate)));
//    }
//
//    @Transactional
//    public UserResponseDto createUser(UserRegistrationFormData candidate) {
//        return userMapper.toUserResponseDto(userDao.create(userMapper.toUser(candidate)));
//    }
//
//    @Transactional
//    public void deleteUser(long id) {
//        userDao.delete(id);
//    }
//}
