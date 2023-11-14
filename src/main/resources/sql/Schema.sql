drop database WashWizards;

Create database WashWizards;

use WashWizards;

create table customer(
                         customerId varchar(35) primary Key,
                         name varchar(35) not null,
                         email text not null,
                         address text not null,
                         telNum int(20) not null,
                         nic varchar(25) not null
);

create table staff(
                      staffId varchar(35) primary key,
                      name varchar(35) not null,
                      email text not null,
                      telNum int(20) not null,
                      role varchar(35) not null
);


create table orders(
                       orderId varchar(35) primary Key,
                       pickupDate date not null,
                       deliverDate date not null,
                       amount double not null,
                       customerId varchar(35),
                       staffId varchar(35),
                       foreign key(customerId) references customer(customerId) on update cascade on delete cascade,
                       foreign key(staffId) references staff(staffId) on update cascade on delete cascade
);

create table payment(
                        paymentId varchar(35) primary Key,
                        amount double not null,
                        paymentDate date not null,
                        orderId varchar(35) not null,
                        foreign key(orderId) references orders(orderId)on update cascade on delete cascade
);

create table item(
                     itemId varchar(35) primary Key,
                     name varchar(35) not null,
                     description text not null,
                     quantity int not null,
                     unitPrice double not null,
                     orderId varchar(35) not null,
                     foreign key(orderId) references orders(orderId)on update cascade on delete cascade
);



create table laundryItem(
                            laundryItemId varchar(35) primary Key,
                            name varchar(35) not null,
                            quantityAvailable int not null,
                            description text not null,
                            unitPrice double not null,
                            itemId varchar(35) not null,
                            foreign key(itemId) references item(itemId)on update cascade on delete cascade
);



create table laundryEquipment(
                                 MachineId varchar(35) primary Key,
                                 MachineType varchar(35) not null,
                                 status varchar(35) not null,
                                 nextRepairDate date not null
);

create table laundryEquipmentMaintenance(
                                            MachineId varchar(35)  ,
                                            StaffId varchar(35) not null,
                                            lastRepairDate date not null,
                                            nextRepairDate date not null,
                                            foreign key(MachineId) references laundryEquipment(MachineId)on update cascade on delete cascade,
                                            foreign key(staffId) references staff(staffId)on update cascade on delete cascade
);

create table salary(
                       SalaryId varchar(25) primary key ,
                       amount double not null,
                       staffId varchar(25) not null,
                       paymentId varchar(25) not null,
                       foreign key (staffId) references staff(staffId)on update cascade on delete cascade ,
                       foreign key (paymentId)references payment(paymentId)on update cascade on delete cascade
);

create table supplier(
                         supplierId varchar(25) primary key ,
                         name text not null,
                         email text not null,
                         telNum int not null,
                         address text not null
);

create table supplierDetails(
                                itemId varchar(25),
                                supplierId varchar(25),
                                foreign key (itemId)references item(itemId)on update cascade on delete cascade ,
                                foreign key (supplierId)references supplier(supplierId)on update cascade on delete cascade

);