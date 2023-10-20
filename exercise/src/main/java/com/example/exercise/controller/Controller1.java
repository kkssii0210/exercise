package com.example.exercise.controller;

import com.example.exercise.domain.Customers;
import com.example.exercise.domain.QCustomers;
import com.example.exercise.domain.QOrders;
import com.example.exercise.repository.CustomerRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/main1")
public class Controller1{

    @Autowired
    private DataSource dataSource;
    @PersistenceContext
    private EntityManager em;
    private final CustomerRepository customerRepository;

    @Autowired
    public Controller1(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
//    @GetMapping("/sub1")
//    public void method1(@RequestParam(value = "id", required = false) Integer customerId) throws SQLException {
//        if (customerId == null) {
//            System.out.println("삭제할 id를 넣어주세요");
//            return;
//        }
//        String sql = """
//                DELETE FROM customers
//                WHERE customerId = ?
//                """;
//        Connection connection = dataSource.getConnection();
//        PreparedStatement statement = connection.prepareStatement(sql);
//        try (connection; statement) {
//            statement.setInt(1, customerId);
//            int rows = statement.executeUpdate(); // insert, delete, update
//
//            if (rows == 1) {
//                System.out.println(rows + "개 레코드 잘 지워짐");
//            } else {
//                System.out.println("지워지지 않음");
//            }
//        }
//    }
    @GetMapping("/sub1")
    @Transactional
    public void method01(@RequestParam(value = "id", required = false) Integer customerId) {
        customerRepository.deleteById(customerId);
    }
    //    @GetMapping("/sub2")
//    public void method2() throws SQLException {
//        String sql = """
//                SELECT CustomerName
//                FROM customers
//                """;
//        Connection connection = dataSource.getConnection();
//        Statement statement = connection.createStatement();
//        ResultSet resultSet = statement.executeQuery(sql);
//        List<String> list = new ArrayList<>();
//        try(connection; statement; resultSet) {
//            while (resultSet.next()) {
//                list.add(resultSet.getString("CustomerName"));
//            }
//        }
//        list.forEach(System.out::println);
//    }
    @GetMapping("sub2")
    public void method2(){
        List<String> findAllMembers = customerRepository.findAllByCustomerName();
        findAllMembers.forEach(System.out::println);
    }
//    @GetMapping("/sub4")
//    public void method4() throws SQLException {
//        String sql = """
//                UPDATE customers
//                SET
//                    customerName = ?,
//                    address = ?
//                WHERE
//                    customerId = ?
//                """;
//        Connection connection = dataSource.getConnection();
//        PreparedStatement statement = connection.prepareStatement(sql);
//        try (connection; statement) {
//            statement.setString(1, "이강인");
//            statement.setString(2, "01077778888");
//            statement.setInt(3, 1);
//            int rows = statement.executeUpdate();
//            if (rows == 1) {
//                System.out.println("잘 변경됨");
//            } else {
//                System.out.println("뭔가 잘 못됨");
//            }
//        }
//    }
    @GetMapping("/sub4")
    @Transactional
    public void method5(@RequestParam("id")Long customerId, Model model){
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
        QCustomers customers = QCustomers.customers;
        jpaQueryFactory
                .update(customers)
                .set(customers.customerName, "이강인")
                .set(customers.address, "서울")
                .where(customers.customerId.eq(customerId))
                .execute();
    }
//    INSERT INTO customers (customerName, address)
//    VALUES ('김민재', '뮌헨');
    @GetMapping("sub5")
    @Transactional
    public void method6(){
        Customers customers = new Customers();
        customers.setCustomerId(92L);
        customers.setCustomerName("김민재");
        customers.setAddress("뮌헨");
        customerRepository.save(customers);
    }
//    @Select("""
//            <script>
//                SELECT COUNT(*)
//                FROM customers
//
//                <if test='country != null'>
//                WHERE country = #{country}
//                </if>
//            </script>
//            """)
//    @GetMapping("sub7")
//    public Long method7(String country){
//        if (country != null) {
//            long l = customerRepository.countByCountry(country);
//            System.out.println("l = " + l);
//            return l;
//        } else {
//            long count = customerRepository.count();
//            System.out.println("count = " + count);
//            return count;
//        }
//    }
    @GetMapping("sub7")
    public Long method7(String country){
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
        QCustomers customers = QCustomers.customers;
        JPAQuery<Long> query = jpaQueryFactory.select(customers.count()).from(customers);
        if (country != null) {
            query.where(customers.country.eq(country));
        }
        Long l = query.fetchOne();
        System.out.println("l = " + l);
        return l;
    }
//    @Select("""
//        <script>
//        SELECT *
//        FROM customers
//        WHERE
//            country IN
//            <foreach collection="args"
//                     item="elem"
//                     separator=", "
//                     open="("
//                     close=")">
//                #{elem}
//            </foreach>
//        </script>
//        """)

    //    @GetMapping("sub8")
//    public List<Customers> method8(@RequestParam("country")List<String> countries){
//        List<Customers> customers = customerRepository.findByCountryIn(countries);
//        List<String> names = customers.stream()
//                .map(Customers::getCustomerName)
//                .toList();
//        names.forEach(System.out::println);
//        return customers;
//    }

    @GetMapping("sub8")
    public List<Customers> method8(@RequestParam("country") List<String> countries) {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
        QCustomers customers = QCustomers.customers;
        List<Customers> customersList = jpaQueryFactory.selectFrom(customers)
                .where(customers.country.in(countries))
                .fetch();
        List<String> names = customersList.stream()
                .map(Customers::getCustomerName)
                .toList();
        names.forEach(System.out::println);
        return customersList;
    }
//    @Select("""
//            <script>
//            <bind name="alterKeyword" value="'%' + keyword + '%'" />
//            SELECT *
//            FROM customers
//            WHERE customerName LIKE #{alterKeyword}
//            </script>
//            """)

//    @GetMapping("sub9")
//    public List<Customers> method9(String keyword) {
//        List<Customers> customers = customerRepository.findByCustomerNameContaining(keyword);
//        List<String> names = customers.stream()
//                .map(Customers::getCustomerName)
//                .toList();
//        names.forEach(System.out::println);
//        return customers;
//    }

    @GetMapping("sub9")
    public List<Customers> method9(String keyword){
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
        QCustomers customers = QCustomers.customers;
        List<Customers> customersList = jpaQueryFactory.selectFrom(customers)
                .where(customers.customerName.contains(keyword))
                .fetch();
        List<String> list = customersList.stream()
                .map(Customers::getCustomerName)
                .toList();
        list.forEach(System.out::println);
        return customersList;
    }
//    SELECT CUSTOMERNAME
//    FROM CUSTOMERS c JOIN ORDERS o
//    ON c.CUSTOMERID = o.CUSTOMERID
//    WHERE o.ORDERDATE=?;
    @GetMapping("sub10")
    public List<String> method10(@RequestParam("orderDate")LocalDate orderDate) {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
        QCustomers customers = QCustomers.customers;
        QOrders orders = QOrders.orders;
        List<String> customerNames = jpaQueryFactory.select(customers.customerName)
                .from(customers)
                .join(customers.orders, orders)
                .where(orders.orderDate.eq(orderDate))
                .fetch();
        customerNames.forEach(System.out::println);
        return customerNames;
    }
//    @Select("""
//            <script>
//                SELECT customerName name, city, country
//                FROM customers
//                <trim prefix="WHERE">
//                    <if test='type == "1"'>
//                        city
//                        <foreach collection="city" item="elem" open=" IN ( "
//                                 separator="," close=")">
//                            #{elem}
//                         </foreach>
//                    </if>
//                    <if test='type == "2"'>
//                        country
//                        <foreach collection="country" item="elem" open=" IN ( "
//                                 separator="," close=")">
//                            #{elem}
//                        </foreach>
//                    </if>
//                </trim>
//                ORDER BY name, country, city
//            </script>
//            """)
    @GetMapping("sub11")
    public List<Customers> method11(String type, List<String> cities, List<String> countries) {
        QCustomers qCustomers = QCustomers.customers;
        BooleanBuilder builder = new BooleanBuilder();
        if ("1".equals(type)) {
            builder.and(qCustomers.city.in(cities));
        } else if ("2".equals(type)) {
            builder.and(qCustomers.country.in(countries));
        }
        return (List<Customers>) customerRepository.findAll(builder,
                qCustomers.customerName.asc(),
                qCustomers.city.asc(),
                qCustomers.country.asc());
    }
}
