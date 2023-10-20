package com.example.exercise.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCustomers is a Querydsl query type for Customers
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCustomers extends EntityPathBase<Customers> {

    private static final long serialVersionUID = 1493384240L;

    public static final QCustomers customers = new QCustomers("customers");

    public final StringPath address = createString("address");

    public final StringPath city = createString("city");

    public final StringPath contactName = createString("contactName");

    public final StringPath country = createString("country");

    public final NumberPath<Long> customerId = createNumber("customerId", Long.class);

    public final StringPath customerName = createString("customerName");

    public final ListPath<Orders, QOrders> orders = this.<Orders, QOrders>createList("orders", Orders.class, QOrders.class, PathInits.DIRECT2);

    public final StringPath postalCode = createString("postalCode");

    public QCustomers(String variable) {
        super(Customers.class, forVariable(variable));
    }

    public QCustomers(Path<? extends Customers> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCustomers(PathMetadata metadata) {
        super(Customers.class, metadata);
    }

}

