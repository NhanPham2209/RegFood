-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th10 11, 2023 lúc 09:46 AM
-- Phiên bản máy phục vụ: 10.4.22-MariaDB
-- Phiên bản PHP: 7.4.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `reg_food`
--

DELIMITER $$
--
-- Các hàm
--
CREATE DEFINER=`root`@`localhost` FUNCTION `CalculateTotalQuantity` (`bill_id` INT) RETURNS INT(11) BEGIN
  DECLARE total INT;
  SELECT SUM(quantity) INTO total
  FROM bills_detail
  WHERE bills_id = bill_id;
  RETURN total;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `address`
--

CREATE TABLE `address` (
  `address_id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `address_detail` varchar(255) NOT NULL,
  `user_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `address`
--

INSERT INTO `address` (`address_id`, `title`, `address_detail`, `user_id`) VALUES
(1, 'Nhà riêng', 'Đông thọ ,TP thái bình', 1),
(2, 'cơ quan', '20 Nguyễn Chánh Cầu Giấy Hà Nội', 3),
(5, 'Cơ quan', 'BigC Thăng Long 220 Trần Duy Hưng', 3),
(6, 'Cơ quan', '77 Nguyễn Chánh Cầu giấy', 3),
(12, 'Nhà riêng', 'Đoàn Kết xẫ Đông Thọ TP Thái Bình', 3);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `bills`
--

CREATE TABLE `bills` (
  `bill_id` int(5) NOT NULL,
  `user_id` int(11) NOT NULL,
  `address_id` int(10) NOT NULL,
  `total_amount` double NOT NULL,
  `promotion_id` int(2) NOT NULL,
  `quantity` int(11) NOT NULL,
  `note` text DEFAULT NULL,
  `status` int(2) NOT NULL,
  `payment` int(2) NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `bills`
--

INSERT INTO `bills` (`bill_id`, `user_id`, `address_id`, `total_amount`, `promotion_id`, `quantity`, `note`, `status`, `payment`, `created_date`) VALUES
(8, 3, 2, 500000, 1, 5, 'gần bigC', 2, 1, '2023-10-08 16:08:05'),
(9, 3, 2, 366, 2, 3, '', 1, 1, '2023-10-01 17:17:25'),
(10, 3, 12, 366, 2, 3, '', 2, 1, '2023-10-01 17:59:23'),
(12, 3, 2, 182.25, 2, 2, 'không cần dụng cụ ăn uống nhựa', 1, 2, '2023-10-09 06:09:19'),
(13, 3, 5, 628.5, 2, 0, '', 0, 1, '2023-10-10 13:58:21'),
(14, 3, 5, 628.5, 2, 2, '', 2, 1, '2023-10-10 13:59:22');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `bills_detail`
--

CREATE TABLE `bills_detail` (
  `id` int(11) NOT NULL,
  `food_id` int(11) NOT NULL,
  `bills_id` int(10) NOT NULL,
  `quantity` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `bills_detail`
--

INSERT INTO `bills_detail` (`id`, `food_id`, `bills_id`, `quantity`) VALUES
(15, 4, 8, 2),
(16, 9, 8, 3),
(17, 7, 9, 1),
(18, 4, 9, 1),
(19, 12, 9, 1),
(20, 12, 10, 1),
(21, 7, 10, 1),
(22, 4, 10, 1),
(26, 13, 12, 1),
(27, 7, 12, 1),
(30, 7, 14, 2);

--
-- Bẫy `bills_detail`
--
DELIMITER $$
CREATE TRIGGER `after_delete_bill_detail` AFTER DELETE ON `bills_detail` FOR EACH ROW BEGIN
  -- Cập nhật totalQuantity trong bảng bills sau khi xóa dữ liệu từ bills_detail
  UPDATE bills b
  SET b.quantity = b.quantity - OLD.quantity
  WHERE b.bill_id = OLD.bills_id;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `category`
--

CREATE TABLE `category` (
  `category_id` int(11) NOT NULL,
  `category_name` varchar(50) NOT NULL,
  `status` tinyint(1) DEFAULT 1,
  `created_date` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `category`
--

INSERT INTO `category` (`category_id`, `category_name`, `status`, `created_date`) VALUES
(1, 'burger', 1, '2023-09-21 17:09:52'),
(2, 'chicken', 1, '2023-09-21 17:10:34'),
(3, 'pizza', 1, '2023-09-21 17:10:46'),
(4, 'dresserts', 1, '2023-09-21 17:10:55'),
(6, 'biryani', 1, '2023-09-23 15:12:15'),
(7, 'kbab', 1, '2023-09-23 15:12:15'),
(8, 'kcchi', 1, '2023-09-23 15:12:15'),
(9, 'noodles', 1, '2023-09-23 15:12:15'),
(10, 'grill', 1, '2023-09-23 15:12:15');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `food`
--

CREATE TABLE `food` (
  `food_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  `food_name` varchar(100) NOT NULL,
  `price` double NOT NULL,
  `description` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `food`
--

INSERT INTO `food` (`food_id`, `category_id`, `food_name`, `price`, `description`) VALUES
(4, 6, 'Maxican Pizza Test Better', 320, 'Ipsum dolor, sit amet consectetur adipisicing elit. Doloribus consectetur ullam in? Beatae, dolorum ad ea deleniti ratione voluptatum similique omnis voluptas tempora optio soluta vero veritatis reiciendis blanditiis architecto. Debitis nesciunt inventore voluptate tempora ea incidunt iste, corporis, quo cumque facere doloribus possimus nostrum sed magni quasi, assumenda autem! Repudiandae nihil magnam provident illo alias vero odit repellendus, ipsa nemo itaque. Aperiam fuga, magnam quia illum minima blanditiis tempore. vero veritatis reiciendis blanditiis architecto. Debitis nesciunt inventore voluptate tempora ea incidunt iste, corporis, quo cumque facere doloribus possimus nostrum sed magni quasi\n\nLorem ipsum dolor sit amet consectetur adipisicing elit Doloribus.\nDolor sit amet consectetur adipisicing elit. Earum itaque nesciunt.\nCorporis quo cumque facere doloribus possimus nostrum sed magni quasi.\nIncidunt iste corporis quo cumque facere doloribus possimus nostrum sed.\nIncidunt iste corporis quo cumque facere doloribus possimus nostrum sed.\nArchitecto Debitis nesciunt inventore voluptate tempora ea.\nEarum itaque nesciunt dolor laudantium placeat sed velit aspernatur.\nLaudantium placeat sed velit aspernatur nobis quos quibusdam distinctio.\nLorem ipsum dolor sit amet consectetur adipisicing elit. Earum itaque nesciunt dolor laudantium placeat sed velit aspernatur, nobis quos quibusdam distinctio voluptatum officia vel sapiente enim, reprehenderit impedit beatae molestias dolorum. A laborum consectetur sed quis exercitationem optio consequatur, unde neque est odit, pariatur quae incidunt quasi dolorem nihil aliquid ut veritatis porro eaque cupiditate voluptatem vel ad! Asperiores, praesentium. sit amet consectetur adipisicing elit. Doloribus consectetur ullam in? Beatae, dolorum ad ea deleniti ratione voluptatum similique omnis voluptas tempora optio soluta\n\nReiciendis blanditiis architecto. Debitis nesciunt inventore voluptate\nLorem ipsum dolor sit amet consectetur adipisicing elit Doloribus.\nDolor sit amet consectetur adipisicing elit. Earum itaque nesciunt.\nCorporis quo cumque facere doloribus possimus nostrum sed magni quasi.\nIncidunt iste corporis quo cumque facere doloribus possimus nostrum sed.\nIncidunt iste corporis quo cumque facere doloribus possimus nostrum sed.\nLorem ipsum dolor, sit amet consectetur adipisicing elit. Doloribus consectetur ullam in? Beatae, dolorum ad ea deleniti ratione voluptatum similique omnis voluptas tempora optio soluta vero veritatis reiciendis blanditiis architecto. Debitis nesciunt inventore voluptate tempora ea incidunt iste, corporis, quo cumque facere doloribus possimus nostrum sed magni quasi, assumenda autem! Repudiandae nihil magnam provident illo alias vero odit repellendus, ipsa nemo itaque. Aperiam fuga, magnam quia illum minima blanditiis tempore.'),
(7, 4, 'Fried Chicken', 99, 'Ipsum dolor, sit amet consectetur adipisicing elit. Doloribus consectetur ullam in? Beatae, dolorum ad ea deleniti ratione voluptatum similique omnis voluptas tempora optio soluta vero veritatis reiciendis blanditiis architecto. Debitis nesciunt inventore voluptate tempora ea incidunt iste, corporis, quo cumque facere doloribus possimus nostrum sed magni quasi, assumenda autem! Repudiandae nihil magnam provident illo alias vero odit repellendus, ipsa nemo itaque. Aperiam fuga, magnam quia illum minima blanditiis tempore. vero veritatis reiciendis blanditiis architecto. Debitis nesciunt inventore voluptate tempora ea incidunt iste, corporis, quo cumque facere doloribus possimus nostrum sed magni quasi\n\nLorem ipsum dolor sit amet consectetur adipisicing elit Doloribus.\nDolor sit amet consectetur adipisicing elit. Earum itaque nesciunt.\nCorporis quo cumque facere doloribus possimus nostrum sed magni quasi.\nIncidunt iste corporis quo cumque facere doloribus possimus nostrum sed.\nIncidunt iste corporis quo cumque facere doloribus possimus nostrum sed.\nArchitecto Debitis nesciunt inventore voluptate tempora ea.\nEarum itaque nesciunt dolor laudantium placeat sed velit aspernatur.\nLaudantium placeat sed velit aspernatur nobis quos quibusdam distinctio.\nLorem ipsum dolor sit amet consectetur adipisicing elit. Earum itaque nesciunt dolor laudantium placeat sed velit aspernatur, nobis quos quibusdam distinctio voluptatum officia vel sapiente enim, reprehenderit impedit beatae molestias dolorum. A laborum consectetur sed quis exercitationem optio consequatur, unde neque est odit, pariatur quae incidunt quasi dolorem nihil aliquid ut veritatis porro eaque cupiditate voluptatem vel ad! Asperiores, praesentium. sit amet consectetur adipisicing elit. Doloribus consectetur ullam in? Beatae, dolorum ad ea deleniti ratione voluptatum similique omnis voluptas tempora optio soluta\n\nReiciendis blanditiis architecto. Debitis nesciunt inventore voluptate\nLorem ipsum dolor sit amet consectetur adipisicing elit Doloribus.\nDolor sit amet consectetur adipisicing elit. Earum itaque nesciunt.\nCorporis quo cumque facere doloribus possimus nostrum sed magni quasi.\nIncidunt iste corporis quo cumque facere doloribus possimus nostrum sed.\nIncidunt iste corporis quo cumque facere doloribus possimus nostrum sed.\nLorem ipsum dolor, sit amet consectetur adipisicing elit. Doloribus consectetur ullam in? Beatae, dolorum ad ea deleniti ratione voluptatum similique omnis voluptas tempora optio soluta vero veritatis reiciendis blanditiis architecto. Debitis nesciunt inventore voluptate tempora ea incidunt iste, corporis, quo cumque facere doloribus possimus nostrum sed magni quasi, assumenda autem! Repudiandae nihil magnam provident illo alias vero odit repellendus, ipsa nemo itaque. Aperiam fuga, magnam quia illum minima blanditiis tempore.'),
(8, 7, 'Mozzarella Sticks', 75, 'Ipsum dolor, sit amet consectetur adipisicing elit. Doloribus consectetur ullam in? Beatae, dolorum ad ea deleniti ratione voluptatum similique omnis voluptas tempora optio soluta vero veritatis reiciendis blanditiis architecto. Debitis nesciunt inventore voluptate tempora ea incidunt iste, corporis, quo cumque facere doloribus possimus nostrum sed magni quasi, assumenda autem! Repudiandae nihil magnam provident illo alias vero odit repellendus, ipsa nemo itaque. Aperiam fuga, magnam quia illum minima blanditiis tempore. vero veritatis reiciendis blanditiis architecto. Debitis nesciunt inventore voluptate tempora ea incidunt iste, corporis, quo cumque facere doloribus possimus nostrum sed magni quasi\n\nLorem ipsum dolor sit amet consectetur adipisicing elit Doloribus.\nDolor sit amet consectetur adipisicing elit. Earum itaque nesciunt.\nCorporis quo cumque facere doloribus possimus nostrum sed magni quasi.\nIncidunt iste corporis quo cumque facere doloribus possimus nostrum sed.\nIncidunt iste corporis quo cumque facere doloribus possimus nostrum sed.\nArchitecto Debitis nesciunt inventore voluptate tempora ea.\nEarum itaque nesciunt dolor laudantium placeat sed velit aspernatur.\nLaudantium placeat sed velit aspernatur nobis quos quibusdam distinctio.\nLorem ipsum dolor sit amet consectetur adipisicing elit. Earum itaque nesciunt dolor laudantium placeat sed velit aspernatur, nobis quos quibusdam distinctio voluptatum officia vel sapiente enim, reprehenderit impedit beatae molestias dolorum. A laborum consectetur sed quis exercitationem optio consequatur, unde neque est odit, pariatur quae incidunt quasi dolorem nihil aliquid ut veritatis porro eaque cupiditate voluptatem vel ad! Asperiores, praesentium. sit amet consectetur adipisicing elit. Doloribus consectetur ullam in? Beatae, dolorum ad ea deleniti ratione voluptatum similique omnis voluptas tempora optio soluta\n\nReiciendis blanditiis architecto. Debitis nesciunt inventore voluptate\nLorem ipsum dolor sit amet consectetur adipisicing elit Doloribus.\nDolor sit amet consectetur adipisicing elit. Earum itaque nesciunt.\nCorporis quo cumque facere doloribus possimus nostrum sed magni quasi.\nIncidunt iste corporis quo cumque facere doloribus possimus nostrum sed.\nIncidunt iste corporis quo cumque facere doloribus possimus nostrum sed.\nLorem ipsum dolor, sit amet consectetur adipisicing elit. Doloribus consectetur ullam in? Beatae, dolorum ad ea deleniti ratione voluptatum similique omnis voluptas tempora optio soluta vero veritatis reiciendis blanditiis architecto. Debitis nesciunt inventore voluptate tempora ea incidunt iste, corporis, quo cumque facere doloribus possimus nostrum sed magni quasi, assumenda autem! Repudiandae nihil magnam provident illo alias vero odit repellendus, ipsa nemo itaque. Aperiam fuga, magnam quia illum minima blanditiis tempore.'),
(9, 8, 'Popcorn Chicken', 69, 'Ipsum dolor, sit amet consectetur adipisicing elit. Doloribus consectetur ullam in? Beatae, dolorum ad ea deleniti ratione voluptatum similique omnis voluptas tempora optio soluta vero veritatis reiciendis blanditiis architecto. Debitis nesciunt inventore voluptate tempora ea incidunt iste, corporis, quo cumque facere doloribus possimus nostrum sed magni quasi, assumenda autem! Repudiandae nihil magnam provident illo alias vero odit repellendus, ipsa nemo itaque. Aperiam fuga, magnam quia illum minima blanditiis tempore. vero veritatis reiciendis blanditiis architecto. Debitis nesciunt inventore voluptate tempora ea incidunt iste, corporis, quo cumque facere doloribus possimus nostrum sed magni quasi\n\nLorem ipsum dolor sit amet consectetur adipisicing elit Doloribus.\nDolor sit amet consectetur adipisicing elit. Earum itaque nesciunt.\nCorporis quo cumque facere doloribus possimus nostrum sed magni quasi.\nIncidunt iste corporis quo cumque facere doloribus possimus nostrum sed.\nIncidunt iste corporis quo cumque facere doloribus possimus nostrum sed.\nArchitecto Debitis nesciunt inventore voluptate tempora ea.\nEarum itaque nesciunt dolor laudantium placeat sed velit aspernatur.\nLaudantium placeat sed velit aspernatur nobis quos quibusdam distinctio.\nLorem ipsum dolor sit amet consectetur adipisicing elit. Earum itaque nesciunt dolor laudantium placeat sed velit aspernatur, nobis quos quibusdam distinctio voluptatum officia vel sapiente enim, reprehenderit impedit beatae molestias dolorum. A laborum consectetur sed quis exercitationem optio consequatur, unde neque est odit, pariatur quae incidunt quasi dolorem nihil aliquid ut veritatis porro eaque cupiditate voluptatem vel ad! Asperiores, praesentium. sit amet consectetur adipisicing elit. Doloribus consectetur ullam in? Beatae, dolorum ad ea deleniti ratione voluptatum similique omnis voluptas tempora optio soluta\n\nReiciendis blanditiis architecto. Debitis nesciunt inventore voluptate\nLorem ipsum dolor sit amet consectetur adipisicing elit Doloribus.\nDolor sit amet consectetur adipisicing elit. Earum itaque nesciunt.\nCorporis quo cumque facere doloribus possimus nostrum sed magni quasi.\nIncidunt iste corporis quo cumque facere doloribus possimus nostrum sed.\nIncidunt iste corporis quo cumque facere doloribus possimus nostrum sed.\nLorem ipsum dolor, sit amet consectetur adipisicing elit. Doloribus consectetur ullam in? Beatae, dolorum ad ea deleniti ratione voluptatum similique omnis voluptas tempora optio soluta vero veritatis reiciendis blanditiis architecto. Debitis nesciunt inventore voluptate tempora ea incidunt iste, corporis, quo cumque facere doloribus possimus nostrum sed magni quasi, assumenda autem! Repudiandae nihil magnam provident illo alias vero odit repellendus, ipsa nemo itaque. Aperiam fuga, magnam quia illum minima blanditiis tempore.'),
(10, 9, 'Onion Rings', 69, 'Ipsum dolor, sit amet consectetur adipisicing elit. Doloribus consectetur ullam in? Beatae, dolorum ad ea deleniti ratione voluptatum similique omnis voluptas tempora optio soluta vero veritatis reiciendis blanditiis architecto. Debitis nesciunt inventore voluptate tempora ea incidunt iste, corporis, quo cumque facere doloribus possimus nostrum sed magni quasi, assumenda autem! Repudiandae nihil magnam provident illo alias vero odit repellendus, ipsa nemo itaque. Aperiam fuga, magnam quia illum minima blanditiis tempore. vero veritatis reiciendis blanditiis architecto. Debitis nesciunt inventore voluptate tempora ea incidunt iste, corporis, quo cumque facere doloribus possimus nostrum sed magni quasi\n\nLorem ipsum dolor sit amet consectetur adipisicing elit Doloribus.\nDolor sit amet consectetur adipisicing elit. Earum itaque nesciunt.\nCorporis quo cumque facere doloribus possimus nostrum sed magni quasi.\nIncidunt iste corporis quo cumque facere doloribus possimus nostrum sed.\nIncidunt iste corporis quo cumque facere doloribus possimus nostrum sed.\nArchitecto Debitis nesciunt inventore voluptate tempora ea.\nEarum itaque nesciunt dolor laudantium placeat sed velit aspernatur.\nLaudantium placeat sed velit aspernatur nobis quos quibusdam distinctio.\nLorem ipsum dolor sit amet consectetur adipisicing elit. Earum itaque nesciunt dolor laudantium placeat sed velit aspernatur, nobis quos quibusdam distinctio voluptatum officia vel sapiente enim, reprehenderit impedit beatae molestias dolorum. A laborum consectetur sed quis exercitationem optio consequatur, unde neque est odit, pariatur quae incidunt quasi dolorem nihil aliquid ut veritatis porro eaque cupiditate voluptatem vel ad! Asperiores, praesentium. sit amet consectetur adipisicing elit. Doloribus consectetur ullam in? Beatae, dolorum ad ea deleniti ratione voluptatum similique omnis voluptas tempora optio soluta\n\nReiciendis blanditiis architecto. Debitis nesciunt inventore voluptate\nLorem ipsum dolor sit amet consectetur adipisicing elit Doloribus.\nDolor sit amet consectetur adipisicing elit. Earum itaque nesciunt.\nCorporis quo cumque facere doloribus possimus nostrum sed magni quasi.\nIncidunt iste corporis quo cumque facere doloribus possimus nostrum sed.\nIncidunt iste corporis quo cumque facere doloribus possimus nostrum sed.\nLorem ipsum dolor, sit amet consectetur adipisicing elit. Doloribus consectetur ullam in? Beatae, dolorum ad ea deleniti ratione voluptatum similique omnis voluptas tempora optio soluta vero veritatis reiciendis blanditiis architecto. Debitis nesciunt inventore voluptate tempora ea incidunt iste, corporis, quo cumque facere doloribus possimus nostrum sed magni quasi, assumenda autem! Repudiandae nihil magnam provident illo alias vero odit repellendus, ipsa nemo itaque. Aperiam fuga, magnam quia illum minima blanditiis tempore.'),
(11, 10, 'Onion Rings', 69, 'Ipsum dolor, sit amet consectetur adipisicing elit. Doloribus consectetur ullam in? Beatae, dolorum ad ea deleniti ratione voluptatum similique omnis voluptas tempora optio soluta vero veritatis reiciendis blanditiis architecto. Debitis nesciunt inventore voluptate tempora ea incidunt iste, corporis, quo cumque facere doloribus possimus nostrum sed magni quasi, assumenda autem! Repudiandae nihil magnam provident illo alias vero odit repellendus, ipsa nemo itaque. Aperiam fuga, magnam quia illum minima blanditiis tempore. vero veritatis reiciendis blanditiis architecto. Debitis nesciunt inventore voluptate tempora ea incidunt iste, corporis, quo cumque facere doloribus possimus nostrum sed magni quasi\n\nLorem ipsum dolor sit amet consectetur adipisicing elit Doloribus.\nDolor sit amet consectetur adipisicing elit. Earum itaque nesciunt.\nCorporis quo cumque facere doloribus possimus nostrum sed magni quasi.\nIncidunt iste corporis quo cumque facere doloribus possimus nostrum sed.\nIncidunt iste corporis quo cumque facere doloribus possimus nostrum sed.\nArchitecto Debitis nesciunt inventore voluptate tempora ea.\nEarum itaque nesciunt dolor laudantium placeat sed velit aspernatur.\nLaudantium placeat sed velit aspernatur nobis quos quibusdam distinctio.\nLorem ipsum dolor sit amet consectetur adipisicing elit. Earum itaque nesciunt dolor laudantium placeat sed velit aspernatur, nobis quos quibusdam distinctio voluptatum officia vel sapiente enim, reprehenderit impedit beatae molestias dolorum. A laborum consectetur sed quis exercitationem optio consequatur, unde neque est odit, pariatur quae incidunt quasi dolorem nihil aliquid ut veritatis porro eaque cupiditate voluptatem vel ad! Asperiores, praesentium. sit amet consectetur adipisicing elit. Doloribus consectetur ullam in? Beatae, dolorum ad ea deleniti ratione voluptatum similique omnis voluptas tempora optio soluta\n\nReiciendis blanditiis architecto. Debitis nesciunt inventore voluptate\nLorem ipsum dolor sit amet consectetur adipisicing elit Doloribus.\nDolor sit amet consectetur adipisicing elit. Earum itaque nesciunt.\nCorporis quo cumque facere doloribus possimus nostrum sed magni quasi.\nIncidunt iste corporis quo cumque facere doloribus possimus nostrum sed.\nIncidunt iste corporis quo cumque facere doloribus possimus nostrum sed.\nLorem ipsum dolor, sit amet consectetur adipisicing elit. Doloribus consectetur ullam in? Beatae, dolorum ad ea deleniti ratione voluptatum similique omnis voluptas tempora optio soluta vero veritatis reiciendis blanditiis architecto. Debitis nesciunt inventore voluptate tempora ea incidunt iste, corporis, quo cumque facere doloribus possimus nostrum sed magni quasi, assumenda autem! Repudiandae nihil magnam provident illo alias vero odit repellendus, ipsa nemo itaque. Aperiam fuga, magnam quia illum minima blanditiis tempore.'),
(12, 2, 'Daria Shevtsova', 69, 'Ipsum dolor, sit amet consectetur adipisicing elit. Doloribus consectetur ullam in? Beatae, dolorum ad ea deleniti ratione voluptatum similique omnis voluptas tempora optio soluta vero veritatis reiciendis blanditiis architecto. Debitis nesciunt inventore voluptate tempora ea incidunt iste, corporis, quo cumque facere doloribus possimus nostrum sed magni quasi, assumenda autem! Repudiandae nihil magnam provident illo alias vero odit repellendus, ipsa nemo itaque. Aperiam fuga, magnam quia illum minima blanditiis tempore. vero veritatis reiciendis blanditiis architecto. Debitis nesciunt inventore voluptate tempora ea incidunt iste, corporis, quo cumque facere doloribus possimus nostrum sed magni quasi\n\nLorem ipsum dolor sit amet consectetur adipisicing elit Doloribus.\nDolor sit amet consectetur adipisicing elit. Earum itaque nesciunt.\nCorporis quo cumque facere doloribus possimus nostrum sed magni quasi.\nIncidunt iste corporis quo cumque facere doloribus possimus nostrum sed.\nIncidunt iste corporis quo cumque facere doloribus possimus nostrum sed.\nArchitecto Debitis nesciunt inventore voluptate tempora ea.\nEarum itaque nesciunt dolor laudantium placeat sed velit aspernatur.\nLaudantium placeat sed velit aspernatur nobis quos quibusdam distinctio.\nLorem ipsum dolor sit amet consectetur adipisicing elit. Earum itaque nesciunt dolor laudantium placeat sed velit aspernatur, nobis quos quibusdam distinctio voluptatum officia vel sapiente enim, reprehenderit impedit beatae molestias dolorum. A laborum consectetur sed quis exercitationem optio consequatur, unde neque est odit, pariatur quae incidunt quasi dolorem nihil aliquid ut veritatis porro eaque cupiditate voluptatem vel ad! Asperiores, praesentium. sit amet consectetur adipisicing elit. Doloribus consectetur ullam in? Beatae, dolorum ad ea deleniti ratione voluptatum similique omnis voluptas tempora optio soluta\n\nReiciendis blanditiis architecto. Debitis nesciunt inventore voluptate\nLorem ipsum dolor sit amet consectetur adipisicing elit Doloribus.\nDolor sit amet consectetur adipisicing elit. Earum itaque nesciunt.\nCorporis quo cumque facere doloribus possimus nostrum sed magni quasi.\nIncidunt iste corporis quo cumque facere doloribus possimus nostrum sed.\nIncidunt iste corporis quo cumque facere doloribus possimus nostrum sed.\nLorem ipsum dolor, sit amet consectetur adipisicing elit. Doloribus consectetur ullam in? Beatae, dolorum ad ea deleniti ratione voluptatum similique omnis voluptas tempora optio soluta vero veritatis reiciendis blanditiis architecto. Debitis nesciunt inventore voluptate tempora ea incidunt iste, corporis, quo cumque facere doloribus possimus nostrum sed magni quasi, assumenda autem! Repudiandae nihil magnam provident illo alias vero odit repellendus, ipsa nemo itaque. Aperiam fuga, magnam quia illum minima blanditiis tempore.'),
(13, 2, 'Spicy Burger', 69, 'Ipsum dolor, sit amet consectetur adipisicing elit. Doloribus consectetur ullam in? Beatae, dolorum ad ea deleniti ratione voluptatum similique omnis voluptas tempora optio soluta vero veritatis reiciendis blanditiis architecto. Debitis nesciunt inventore voluptate tempora ea incidunt iste, corporis, quo cumque facere doloribus possimus nostrum sed magni quasi, assumenda autem! Repudiandae nihil magnam provident illo alias vero odit repellendus, ipsa nemo itaque. Aperiam fuga, magnam quia illum minima blanditiis tempore. vero veritatis reiciendis blanditiis architecto. Debitis nesciunt inventore voluptate tempora ea incidunt iste, corporis, quo cumque facere doloribus possimus nostrum sed magni quasi\n\nLorem ipsum dolor sit amet consectetur adipisicing elit Doloribus.\nDolor sit amet consectetur adipisicing elit. Earum itaque nesciunt.\nCorporis quo cumque facere doloribus possimus nostrum sed magni quasi.\nIncidunt iste corporis quo cumque facere doloribus possimus nostrum sed.\nIncidunt iste corporis quo cumque facere doloribus possimus nostrum sed.\nArchitecto Debitis nesciunt inventore voluptate tempora ea.\nEarum itaque nesciunt dolor laudantium placeat sed velit aspernatur.\nLaudantium placeat sed velit aspernatur nobis quos quibusdam distinctio.\nLorem ipsum dolor sit amet consectetur adipisicing elit. Earum itaque nesciunt dolor laudantium placeat sed velit aspernatur, nobis quos quibusdam distinctio voluptatum officia vel sapiente enim, reprehenderit impedit beatae molestias dolorum. A laborum consectetur sed quis exercitationem optio consequatur, unde neque est odit, pariatur quae incidunt quasi dolorem nihil aliquid ut veritatis porro eaque cupiditate voluptatem vel ad! Asperiores, praesentium. sit amet consectetur adipisicing elit. Doloribus consectetur ullam in? Beatae, dolorum ad ea deleniti ratione voluptatum similique omnis voluptas tempora optio soluta\n\nReiciendis blanditiis architecto. Debitis nesciunt inventore voluptate\nLorem ipsum dolor sit amet consectetur adipisicing elit Doloribus.\nDolor sit amet consectetur adipisicing elit. Earum itaque nesciunt.\nCorporis quo cumque facere doloribus possimus nostrum sed magni quasi.\nIncidunt iste corporis quo cumque facere doloribus possimus nostrum sed.\nIncidunt iste corporis quo cumque facere doloribus possimus nostrum sed.\nLorem ipsum dolor, sit amet consectetur adipisicing elit. Doloribus consectetur ullam in? Beatae, dolorum ad ea deleniti ratione voluptatum similique omnis voluptas tempora optio soluta vero veritatis reiciendis blanditiis architecto. Debitis nesciunt inventore voluptate tempora ea incidunt iste, corporis, quo cumque facere doloribus possimus nostrum sed magni quasi, assumenda autem! Repudiandae nihil magnam provident illo alias vero odit repellendus, ipsa nemo itaque. Aperiam fuga, magnam quia illum minima blanditiis tempore.');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `food_image`
--

CREATE TABLE `food_image` (
  `id` int(11) NOT NULL,
  `food_id` int(11) NOT NULL,
  `image` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `food_image`
--

INSERT INTO `food_image` (`id`, `food_id`, `image`) VALUES
(29, 4, 'banner_bg.jpg'),
(30, 4, 'blog_2.jpg'),
(31, 4, 'blog_3.jpg'),
(32, 4, 'blog_4.jpg'),
(41, 7, 'blog_4.jpg'),
(42, 7, 'blog_5.jpg'),
(43, 7, 'blog_details.jpg'),
(44, 7, 'cart_offer_img.jpg'),
(45, 8, 'download_img.png'),
(46, 8, 'download_slider_1.jpg'),
(47, 8, 'download_slider_2.jpg'),
(48, 8, 'download_slider_3.jpg'),
(49, 9, 'menu2_img_6.jpg'),
(50, 9, 'menu2_img_7.jpg'),
(51, 9, 'offer_item_img2.jpg'),
(52, 9, 'offer_item_img3.jpg'),
(57, 11, 'download_slider_4.jpg'),
(58, 11, 'menu2_img_5.jpg'),
(59, 11, 'menu2_img_6.jpg'),
(60, 11, 'menu2_img_8.jpg'),
(61, 10, 'cart_offer_img.jpg'),
(62, 10, 'download_slider_4.jpg'),
(63, 10, 'menu2_img_1.jpg'),
(64, 10, 'menu2_img_2.jpg'),
(69, 12, 'banner_bg.jpg'),
(70, 12, 'blog_5.jpg'),
(71, 12, 'blog_6.jpg'),
(72, 12, 'blog_details.jpg'),
(73, 13, 'download_img.png'),
(74, 13, 'download_slider_1.jpg'),
(75, 13, 'download_slider_2.jpg'),
(76, 13, 'menu2_img_4.jpg');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `posts`
--

CREATE TABLE `posts` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `content` text NOT NULL,
  `image` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `promotion`
--

CREATE TABLE `promotion` (
  `id` int(2) NOT NULL,
  `percent` int(2) NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `promotion`
--

INSERT INTO `promotion` (`id`, `percent`, `status`) VALUES
(1, 20, 1),
(2, 25, 1),
(3, 15, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `rating_food`
--

CREATE TABLE `rating_food` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `food_id` int(11) NOT NULL,
  `content` text NOT NULL,
  `rate_point` int(11) NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `rating_food`
--

INSERT INTO `rating_food` (`id`, `user_id`, `food_id`, `content`, `rate_point`, `created_date`) VALUES
(1, 3, 13, 'Món ăn thật tuyệt vời , các bạ nên thử', 5, '2023-10-03 15:41:12'),
(2, 2, 13, 'Rất đậm đà hợp khẩu vị', 4, '2023-10-03 15:41:12'),
(3, 3, 4, 'Rất ngon', 4, '2023-10-09 05:22:02'),
(4, 3, 7, 'Món này ngon', 4, '2023-10-09 05:22:02'),
(5, 3, 8, 'không có chỗ chê :))', 4, '2023-10-09 05:22:02'),
(6, 3, 9, 'không ngon', 3, '2023-10-09 05:22:02'),
(7, 3, 10, 'ngon', 5, '2023-10-09 05:22:02'),
(8, 3, 11, 'tui rất thích', 4, '2023-10-09 05:22:02'),
(9, 3, 12, 'không tồi', 3, '2023-10-09 05:22:02'),
(10, 3, 13, 'hahaha', 5, '2023-10-09 05:22:02'),
(11, 3, 13, 'đánh giá lần thứ 3 cho quán', 5, '2023-10-09 06:54:27'),
(12, 3, 13, 'đánh giá lần thứ 4 cho quán nha', 5, '2023-10-09 06:55:24');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `rating_restaurant`
--

CREATE TABLE `rating_restaurant` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `restaurant_id` int(10) NOT NULL,
  `content` text NOT NULL,
  `rate_point` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `rating_restaurant`
--

INSERT INTO `rating_restaurant` (`id`, `user_id`, `restaurant_id`, `content`, `rate_point`) VALUES
(4, 3, 1, 'Lorem ipsum dolor sit amet consectetur adipisicing elit. Aut accusamuspraesentium quaerat nihil magnam a porro eaque numquam', 5),
(5, 5, 1, 'Lorem ipsum dolor sit amet consectetur adipisicing elit. Aut accusamuspraesentium quaerat nihil magnam a porro eaque numquam', 4),
(6, 7, 1, 'Lorem ipsum dolor sit amet consectetur adipisicing elit. Aut accusamuspraesentium quaerat nihil magnam a porro eaque numquam', 5);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `restaurant`
--

CREATE TABLE `restaurant` (
  `restaurant_id` int(11) NOT NULL,
  `restaurant_name` varchar(255) NOT NULL,
  `image_logo` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `number_phone` varchar(10) NOT NULL,
  `email` varchar(100) NOT NULL,
  `description` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `restaurant`
--

INSERT INTO `restaurant` (`restaurant_id`, `restaurant_name`, `image_logo`, `address`, `number_phone`, `email`, `description`) VALUES
(1, 'RegFood', 'logo.png', 'Blackwell Street,Dry Creek,Alaska', '0899947749', 'themefaxbd@gmail.com', 'Delicious Foods With Wonderful Eating');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `role`
--

CREATE TABLE `role` (
  `role_id` int(2) NOT NULL,
  `role_name` varchar(50) NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `role`
--

INSERT INTO `role` (`role_id`, `role_name`, `created_date`) VALUES
(1, 'ADMIN', '2023-09-12 12:15:25'),
(2, 'USER', '2023-09-12 12:15:25');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `user_id` int(10) NOT NULL,
  `fullname` varchar(255) NOT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `numberphone` varchar(10) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `role_id` int(2) NOT NULL DEFAULT 2
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`user_id`, `fullname`, `avatar`, `numberphone`, `email`, `password`, `role_id`) VALUES
(1, 'Phạm Đức Nhân', 'avatardefault.jpg', '0356366428', 'nhandaika@gmail.com', '$2y$10$puScrVqfNEk5rEK15qAby.fMUvWSUVhOLU3ujBjdPCETzXtRYUSaS', 1),
(2, 'Hoàng Văn Cường', 'avatardefault.jpg', '022444', 'cuong@gmail.com', '$2y$10$puScrVqfNEk5rEK15qAby.fMUvWSUVhOLU3ujBjdPCETzXtRYUSaS', 1),
(3, 'Tran Van Thai', 'client_4.png', '0122111', 'thai@gmail.com', '$2a$10$aC3W6Xjnk/tDrxEvf4jgc.PtPnIndCsv4/8Cgo6ik8t/6Lhy3X8n6', 2),
(5, 'Tran Thi Nhung', 'avatardefault.jpg', '012211122', 'Nhung@gmail.com', '$2y$10$puScrVqfNEk5rEK15qAby.fMUvWSUVhOLU3ujBjdPCETzXtRYUSaS', 2),
(6, 'Trần Trung Quốc', 'avatardefault.jpg', '038636444', 'quoc@gmail.com', '123456', 2),
(7, 'Phạm Văn Hà', 'avatardefault.jpg', '011212121', 'ha@gmail.com', '$2a$10$Jw9PnjSFbFODL328tYKiFuz0fMDjDuulZkXE8.JPVydKtU47xZ5oe', 2);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `address`
--
ALTER TABLE `address`
  ADD PRIMARY KEY (`address_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Chỉ mục cho bảng `bills`
--
ALTER TABLE `bills`
  ADD PRIMARY KEY (`bill_id`),
  ADD KEY `address_id` (`address_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `promotion` (`promotion_id`);

--
-- Chỉ mục cho bảng `bills_detail`
--
ALTER TABLE `bills_detail`
  ADD PRIMARY KEY (`id`),
  ADD KEY `bills_detail_ibfk_1` (`bills_id`),
  ADD KEY `food_id` (`food_id`);

--
-- Chỉ mục cho bảng `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`category_id`);

--
-- Chỉ mục cho bảng `food`
--
ALTER TABLE `food`
  ADD PRIMARY KEY (`food_id`),
  ADD KEY `category_id` (`category_id`);

--
-- Chỉ mục cho bảng `food_image`
--
ALTER TABLE `food_image`
  ADD PRIMARY KEY (`id`),
  ADD KEY `food_id` (`food_id`);

--
-- Chỉ mục cho bảng `posts`
--
ALTER TABLE `posts`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Chỉ mục cho bảng `promotion`
--
ALTER TABLE `promotion`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `rating_food`
--
ALTER TABLE `rating_food`
  ADD PRIMARY KEY (`id`),
  ADD KEY `food_id` (`food_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Chỉ mục cho bảng `rating_restaurant`
--
ALTER TABLE `rating_restaurant`
  ADD PRIMARY KEY (`id`),
  ADD KEY `restaurant_id` (`restaurant_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Chỉ mục cho bảng `restaurant`
--
ALTER TABLE `restaurant`
  ADD PRIMARY KEY (`restaurant_id`);

--
-- Chỉ mục cho bảng `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`role_id`);

--
-- Chỉ mục cho bảng `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD KEY `role_id` (`role_id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `address`
--
ALTER TABLE `address`
  MODIFY `address_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT cho bảng `bills`
--
ALTER TABLE `bills`
  MODIFY `bill_id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT cho bảng `bills_detail`
--
ALTER TABLE `bills_detail`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT cho bảng `category`
--
ALTER TABLE `category`
  MODIFY `category_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT cho bảng `food`
--
ALTER TABLE `food`
  MODIFY `food_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT cho bảng `food_image`
--
ALTER TABLE `food_image`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=89;

--
-- AUTO_INCREMENT cho bảng `posts`
--
ALTER TABLE `posts`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `promotion`
--
ALTER TABLE `promotion`
  MODIFY `id` int(2) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `rating_food`
--
ALTER TABLE `rating_food`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT cho bảng `rating_restaurant`
--
ALTER TABLE `rating_restaurant`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT cho bảng `restaurant`
--
ALTER TABLE `restaurant`
  MODIFY `restaurant_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT cho bảng `role`
--
ALTER TABLE `role`
  MODIFY `role_id` int(2) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `address`
--
ALTER TABLE `address`
  ADD CONSTRAINT `address_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `bills`
--
ALTER TABLE `bills`
  ADD CONSTRAINT `bills_ibfk_1` FOREIGN KEY (`address_id`) REFERENCES `address` (`address_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `bills_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `bills_ibfk_3` FOREIGN KEY (`promotion_id`) REFERENCES `promotion` (`id`);

--
-- Các ràng buộc cho bảng `bills_detail`
--
ALTER TABLE `bills_detail`
  ADD CONSTRAINT `bills_detail_ibfk_1` FOREIGN KEY (`bills_id`) REFERENCES `bills` (`bill_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `bills_detail_ibfk_2` FOREIGN KEY (`food_id`) REFERENCES `food` (`food_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `food`
--
ALTER TABLE `food`
  ADD CONSTRAINT `food_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `food_image`
--
ALTER TABLE `food_image`
  ADD CONSTRAINT `food_image_ibfk_1` FOREIGN KEY (`food_id`) REFERENCES `food` (`food_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `posts`
--
ALTER TABLE `posts`
  ADD CONSTRAINT `posts_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- Các ràng buộc cho bảng `rating_food`
--
ALTER TABLE `rating_food`
  ADD CONSTRAINT `rating_food_ibfk_1` FOREIGN KEY (`food_id`) REFERENCES `food` (`food_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `rating_food_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `rating_restaurant`
--
ALTER TABLE `rating_restaurant`
  ADD CONSTRAINT `rating_restaurant_ibfk_1` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`restaurant_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `rating_restaurant_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `users_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
