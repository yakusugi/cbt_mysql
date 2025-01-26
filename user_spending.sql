CREATE TABLE `user_spending` (
  `spending_id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `spending_date` date DEFAULT NULL,
  `store_name` varchar(255) DEFAULT NULL,
  `product_name` varchar(255) DEFAULT NULL,
  `product_type` varchar(255) DEFAULT NULL,
  `vat_rate` decimal(5,2) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `note` text,
  `currency_code` varchar(3) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `creation_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`spending_id`),
  KEY `fk_user_email` (`email`),
  CONSTRAINT `fk_user_email` FOREIGN KEY (`email`) REFERENCES `users` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=547 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;