SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

USE `jewelry-rent-system`;

SET NAMES utf8mb4;

INSERT INTO `branches` (`id`, `address`, `telephone`) VALUES
(1,	'57 avenue, 36',	'7569813'),
(2,	'63 street, 72',	'1489327'),
(3,	'11 avenue, 32',	'5478219');

INSERT INTO `clients` (`id`, `address`, `name`, `passport_number`, `second_name`, `surname`, `telephone`) VALUES
(1,	'39 avenue, 89',	'Peter',	'SD2158932',	'Paul',	'Corn',	'51478935'),
(2,	'45 street, 52',	'John',	'RT2145789',	'Michael',	'Redgi',	'1247896'),
(3,	'78 avenue, 1',	'Liza',	'TY147856',	'Danny',	'Kold',	'2147853'),
(4,	'55 street, 8',	'Julia',	'RF5120147',	'Ringo',	'Tree',	'5123079'),
(5,	'99 avenue, 96',	'Michael',	'PF4158761',	'Peter',	'Lemon',	'5120397');

INSERT INTO `employees` (`id`, `login`, `name`, `password`, `position`, `role`, `salary`, `second_name`, `surname`, `branch_id`) VALUES
(1,	'pbell',	'Peter',	'499222c9d1335a6eae0ebf72cccbd926d5e95e41656438a1cccd8e265c1642c158af2d9c3499790b',	'Manager',	'ROLE_ADMIN',	1000,	'Michael',	'Bell',	1), -- pbellpasswd
(2,	'lkaster',	'Lucy',	'b077b698c203490212010f11639d5ac54a8f3e6e2d6b97ed3be43480e1cd7e5848ba69ac0f7cea6e',	'Position',	'ROLE_MANAGER',	750,	'John',	'Kaster',	1), -- lkasterpasswd
(3,	'pmatcher',	'Paul',	'3e6256d9660e292b3d57150d1f47dc73e03f17ebf86ae266d89d02c10d067e5dd744d561fc217ad7',	'Position',	'ROLE_SELLER',	500,	'Kenny',	'Matcher',	1); -- pmatcherpasswd

INSERT INTO `jewelries` (`id`, `cost_per_day`, `days_rental`, `description`, `name`, `picture_url`, `producer`, `status`, `type`, `weight`, `branch_id`) VALUES
(1,	1000,	1,	'Description',	'Ring 1',	'None',	'PR1',	'Available',	'Ring',	100,	1),
(2,	2000,	1,	'Description',	'Ring 2',	'None',	'PR1',	'Available',	'Ring',	100,	1),
(3,	500,	1,	'Description',	'Earring',	'None',	'PR2',	'Not available',	'Earring',	50,	2),
(4,	750,	1,	'Description',	'Necklace',	'None',	'PR3',	'Booked',	'Necklace',	75,	3),
(5,	350,	1,	'Description',	'Bracelet',	'None',	'PR4',	'Booked',	'Bracelet',	90,	3);

INSERT INTO `jewelry_material` (`jewelry_id`, `material_id`) VALUES
(1,	1),
(2,	2),
(3,	3),
(4,	4),
(5,	5);

INSERT INTO `materials` (`id`, `description`, `name`, `material_id`) VALUES
(1,	'Gold',	'Gold',	NULL),
(2,	'Pink Gold',	'Pink Gold',	1),
(3,	'Platinum',	'Platinum',	NULL),
(4,	'Diamond',	'Diamond',	NULL),
(5,	'Silver',	'Silver',	NULL);

INSERT INTO `orders` (`id`, `cost`, `days_rent`, `rent_date`, `status`, `client_id`, `employee_id`, `jewelry_id`) VALUES
(1,	10000,	10,	'2021-03-01 00:00:00',	'Closed',	1,	3,	1),
(2,	16000,	8,	'2021-03-02 00:00:00',	'Closed',	2,	3,	2),
(3,	1500,	3,	'2021-03-10 00:00:00',	'Closed',	3,	3,	3),
(4,	3750,	5,	'2021-03-16 00:00:00',	'Closed',	4,	3,	4),
(5,	4900,	14,	'2021-03-08 00:00:00',	'Closed',	5,	3,	5);