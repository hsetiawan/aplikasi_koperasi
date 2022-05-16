-- MariaDB dump 10.17  Distrib 10.4.14-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: db_rptra
-- ------------------------------------------------------
-- Server version	10.4.14-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `anggota`
--

DROP TABLE IF EXISTS `anggota`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `anggota` (
  `id_anggota` varchar(30) NOT NULL,
  `nama_anggota` varchar(50) DEFAULT NULL,
  `alamat` varchar(255) DEFAULT NULL,
  `username` varchar(30) DEFAULT NULL,
  `password` varchar(30) DEFAULT NULL,
  `no_hp` varchar(12) DEFAULT NULL,
  PRIMARY KEY (`id_anggota`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `anggota`
--

LOCK TABLES `anggota` WRITE;
/*!40000 ALTER TABLE `anggota` DISABLE KEYS */;
INSERT INTO `anggota` VALUES ('A00001','HERU IRAWAN','DRAMAGA','ADMINISTRATOR','123456','085744441234'),('A00002','Wora','bogor','wora','123','123321');
/*!40000 ALTER TABLE `anggota` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dokumentasi`
--

DROP TABLE IF EXISTS `dokumentasi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dokumentasi` (
  `id_dokumentasi` varchar(300) NOT NULL,
  `id_kegiatan` varchar(30) DEFAULT NULL,
  `foto` varchar(100) DEFAULT NULL,
  `deskripsi` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_dokumentasi`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dokumentasi`
--

LOCK TABLES `dokumentasi` WRITE;
/*!40000 ALTER TABLE `dokumentasi` DISABLE KEYS */;
INSERT INTO `dokumentasi` VALUES ('D00003','K00001 - Vaksin Warga Petojo','',''),('D00004','K00001 - Vaksin Warga Petojo','blue-certificate-border-watermarked.jpg','ini deskripsi'),('D00005','K00001 - Vaksin Warga Petojo','DSC_0213.JPG','ini adalah photo'),('D00006','K00001-Vaksin Warga Petojo','10322676_10201885077208320_4944139774282586519_n.jpg','ini dan itu bahahha'),('D00007','K00001-Vaksin Warga Petojo','10322676_10201885077208320_4944139774282586519_n.jpg','ini diubah'),('D00008','Pilih ','template.jpg','ini adalah contoh banner dan banner'),('D00009','K00001','blue-certificate-border-watermarked.png','ini adalah contoh bank dan itu juga \nadalah bukan contoh bank'),('D00010','K00001','10882378_10202010706028962_4850103528373804410_n.jpg','ini dan itu ');
/*!40000 ALTER TABLE `dokumentasi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventaris`
--

DROP TABLE IF EXISTS `inventaris`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `inventaris` (
  `id_barang` varchar(20) NOT NULL,
  `nama_barang` varchar(50) DEFAULT NULL,
  `tanggal_pengadaan` date DEFAULT NULL,
  `kondisi` varchar(100) DEFAULT NULL,
  `keterangan` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_barang`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventaris`
--

LOCK TABLES `inventaris` WRITE;
/*!40000 ALTER TABLE `inventaris` DISABLE KEYS */;
INSERT INTO `inventaris` VALUES ('B00001','SENDOK','2021-09-01','BAIK','Belakangan ini di kota Jakarta sedang digalakkan RPTRA (Ruang Publik Terbuka Ramah Anak)\nyang sesuai namanya, diklaim sebagai ruang publik yang ramah anak. Namun pada kenyataannya\ntidak semua RPTRA ramah anak. ');
/*!40000 ALTER TABLE `inventaris` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kegiatan`
--

DROP TABLE IF EXISTS `kegiatan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kegiatan` (
  `id_kegiatan` varchar(30) NOT NULL,
  `tgl_kegiatan` varchar(30) DEFAULT NULL,
  `judul_kegiatan` varchar(100) DEFAULT NULL,
  `jam_mulai` time DEFAULT NULL,
  `jam_berakhir` time DEFAULT NULL,
  `deskripsi` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_kegiatan`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kegiatan`
--

LOCK TABLES `kegiatan` WRITE;
/*!40000 ALTER TABLE `kegiatan` DISABLE KEYS */;
INSERT INTO `kegiatan` VALUES ('K00001','2021-09-06','Vaksin Warga Petojo','13:00:00','15:00:00','Vaksin untuk warga petojo');
/*!40000 ALTER TABLE `kegiatan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pengunjung`
--

DROP TABLE IF EXISTS `pengunjung`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pengunjung` (
  `id_pengunjung` varchar(30) NOT NULL,
  `nama_pengunjung` varchar(50) DEFAULT NULL,
  `telp` varchar(12) DEFAULT NULL,
  `alamat` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id_pengunjung`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pengunjung`
--

LOCK TABLES `pengunjung` WRITE;
/*!40000 ALTER TABLE `pengunjung` DISABLE KEYS */;
INSERT INTO `pengunjung` VALUES ('P00001','Yono','085781830394','jalan ini dan itu');
/*!40000 ALTER TABLE `pengunjung` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `piket`
--

DROP TABLE IF EXISTS `piket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `piket` (
  `id_piket` varchar(30) NOT NULL,
  `id_anggota` varchar(30) DEFAULT NULL,
  `jadwal_masuk` datetime DEFAULT NULL,
  `tugas` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_piket`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `piket`
--

LOCK TABLES `piket` WRITE;
/*!40000 ALTER TABLE `piket` DISABLE KEYS */;
INSERT INTO `piket` VALUES ('T00002','A00001','2021-09-19 00:00:00','membersihkan atap sekolah'),('T00003','A00002','2021-09-16 00:00:00',''),('T00004','A00002','2021-09-11 00:00:00',''),('T00005','A00001','2021-09-03 00:00:00',''),('T00006','A00002','2021-09-10 00:00:00',''),('T00007','A00002','2021-09-11 00:00:00','\n'),('T00008','A00002','2021-09-09 00:00:00',''),('T00009','A00002','2021-09-05 00:00:00',''),('T00010','A00002','2021-09-10 00:00:00','\n'),('T00011','A00002','2021-09-03 00:00:00',''),('T00012','A00001','2021-09-09 00:00:00',''),('T00013','A00002','2021-09-01 00:00:00','sds'),('T00014','A00001','2021-09-03 00:00:00',''),('T00015','Pilih ','2021-09-09 00:00:00',''),('T00016','A00001','2021-09-09 00:00:00',''),('T00017','A00002','2021-09-15 00:00:00',''),('T00018','A00001','2021-09-03 00:00:00',''),('T00019','A00001','2021-09-02 00:00:00','');
/*!40000 ALTER TABLE `piket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `target`
--

DROP TABLE IF EXISTS `target`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `target` (
  `kd_target` varchar(20) NOT NULL,
  `bln_produksi` varchar(30) DEFAULT NULL,
  `kd_barang` varchar(30) DEFAULT NULL,
  `nama_barang` varchar(50) DEFAULT NULL,
  `jml_target` int(30) DEFAULT NULL,
  PRIMARY KEY (`kd_target`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `target`
--

LOCK TABLES `target` WRITE;
/*!40000 ALTER TABLE `target` DISABLE KEYS */;
/*!40000 ALTER TABLE `target` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-09-15  8:30:13
