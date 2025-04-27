# 데이터 베이스 삭제
DROP DATABASE IF EXISTS text_board;

# 데이터 베이스 생성
CREATE DATABASE text_board;

# 데이터 베이스 선택
USE text_board;

# article 테이블 생성
CREATE TABLE article (
	id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	regDate DATETIME NOT NULL,
	updateDate DATETIME NOT NULL,
	subject CHAR(100) NOT NULL,
	content TEXT NOT NULL
);

# 게시물 테이블에 memberId 칼럼 추가
ALTER TABLE article ADD COLUMN memberId INT UNSIGNED NOT NULL AFTER updateDate;

# 게시물 테이블에 hit 칼럼 추가
ALTER TABLE article ADD COLUMN hit INT UNSIGNED NOT NULL AFTER content;

# 게시물 테이블에 boardId 칼럼 추가
ALTER TABLE article ADD COLUMN boardId INT UNSIGNED NOT NULL AFTER memberId;

# 게시물 데이터 조회
SELECT *
FROM article;

# 날짜 출력 포맷 변경
SELECT A.id,
DATE_FORMAT(A.regDate, '%Y-%m-%d %H:%i:%s') AS regDate,
DATE_FORMAT(A.updateDate, '%Y-%m-%d %H:%i:%s') AS updateDate,
A.subject,
A.content
FROM article AS A;

# member 테이블 생성
CREATE TABLE `member` (
	id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	regDate DATETIME NOT NULL,
	updateDate DATETIME NOT NULL,
	username CHAR(100) NOT NULL UNIQUE,
	password CHAR(150) NOT NULL,
	name CHAR(100) NOT NULL
);

# 회원 테스트 데이터
INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
username = 'admin',
password = 'admin',
name = '관리자';

INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
username = 'user1',
password = '1234',
name = '양관식';

INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
username = 'user2',
password = '1234',
name = '오애순';

INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
username = 'user3',
password = '1234',
name = '양금명';

SELECT *
FROM member;

# Article 테이블과 Member 테이블을 inner join
SELECT A.*,
M.name AS writerName
FROM article AS A
INNER JOIN `member` AS M
ON A.memberId = M.id;

# Board 테이블 생성
CREATE TABLE board (
	id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	regDate DATETIME NOT NULL,
	updateDate DATETIME NOT NULL,
	`code` VARCHAR(50) NOT NULL UNIQUE COMMENT '게시판 코드(예 : notice, free, qna)',
	`name` VARCHAR(100) NOT NULL UNIQUE COMMENT '게시판 이름(예 : 공지사항, 자유게시판)'
);

# 테스트 게시판 생성
INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`code` = 'free',
`name` = '자유게시판';

INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`code` = 'notice',
`name` = '공지사항';


# 게시물 테스트 데이터 추가
INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
memberId = 1,
boardId = 1,
subject = '제목1',
content = '내용1',
hit = 10;

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
memberId = 1,
boardId = 1,
subject = '제목2',
content = '내용2',
hit = 0;

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
memberId = 1,
boardId = 2,
subject = '제목3',
content = '내용3',
hit = 30;

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
memberId = 2,
boardId = 2,
subject = '제목4',
content = '내용4',
hit = 3;

SELECT A.*, M.name AS writerName,
B.name AS boardName
FROM article AS A
INNER JOIN `member` AS M
ON A.memberId = M.id
INNER JOIN board AS B
ON A.boardId = B.id
ORDER BY id DESC

SELECT A.*, M.name AS writerName,
B.name AS boardName
FROM article AS A
INNER JOIN `member` AS M
ON A.memberId = M.id
INNER JOIN board AS B
ON A.boardId = B.id
WHERE A.boardId = 2
ORDER BY id DESC