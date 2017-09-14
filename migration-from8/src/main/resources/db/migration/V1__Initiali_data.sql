DELETE FROM affiliations;
DELETE FROM employees;
DELETE FROM departments;
DELETE FROM companies;

INSERT INTO companies(id, name) VALUES
  (1, 'サンプル工業'),
  (2, '東日本金属')
;

INSERT INTO departments(id, company_id, name) VALUES 
  (100001, 1, '経営企画部'),
  (100002, 1, '営業部'),
  (100003, 1, '制作部')
;

INSERT INTO departments(id, company_id, name) VALUES 
  (100004, 2, '経理部'),
  (100005, 2, '資材調達部'),
  (100006, 2, '営業部'),
  (100007, 2, '製品加工部')
;

INSERT INTO employees(id, name) VALUES
  (1001, '清水 幹明'),
  (1002, '内藤 紗佳'),
  (1003, '吉本 蒼真'),
  (1004, '大内 夕利'),
  (1005, '森下 陽樹'),
  (1006, '田中 哲功'),
  (1007, '稲葉 天音'),
  (1008, '梶原 紗帆'),
  (1009, '安井 真永'),
  (1010, '佐野 三佳'),
  (1011, '岡崎 生帆'),
  (1012, '大槻 乃蒼'),
  (1013, '高野 哲宏'),
  (1014, '長沢 杏弥'),
  (1015, '岡 理衣'),
  (1016, '小出 海斗'),
  (1017, '小川 研介'),
  (1018, '柴田 咲圭'),
  (1019, '栗原 珊瑚'),
  (1020, '菊地 政寛'),
  (1021, '徳田 保太'),
  (1022, '馬場 杏花'),
  (1023, '三好 優摩'),
  (1024, '新田 晶大'),
  (1025, '小倉 海江'),
  (1026, '浅井 里砂'),
  (1027, '渡辺 竜彦'),
  (1028, '杉原 政宏'),
  (1029, '竹下 翔哉'),
  (1030, '河村 亜澄'),
  (1031, '奥村 夕愛'),
  (1032, '竹内 夕楓'),
  (1033, '大平 菜香'),
  (1034, '永野 淡野'),
  (1035, '中野 康太'),
  (1036, '山村 奈都'),
  (1037, '宮川 晴希'),
  (1038, '辻 友哉'),
  (1039, '松浦 美春'),
  (1040, '加納 錬')
;

INSERT INTO affiliations(employee_id, department_id) VALUES
  (1, 100002),
  (2, 100001),
  (3, 100003),
  (4, 100002),
  (5, 100002),
  (6, 100002),
  (7, 100003),
  (8, 100001),
  (9, 100001),
  (10, 100001),
  (11, 100003),
  (12, 100002),
  (13, 100003),
  (14, 100002),
  (15, 100003),
  (16, 100001),
  (17, 100001),
  (18, 100003),
  (19, 100002),
  (20, 100003),
  (21, 100001),
  (22, 100003),
  (23, 100003),
  (24, 100003),
  (25, 100002),
  (26, 100001),
  (27, 100002),
  (28, 100003),
  (29, 100003),
  (30, 100003),
  (31, 100002),
  (32, 100003),
  (33, 100001),
  (34, 100003),
  (35, 100002),
  (36, 100003),
  (37, 100001),
  (38, 100002),
  (39, 100002),
  (40, 100003)
;

INSERT INTO employees(id, name) VALUES
  (1041, '中川 ふみ子'),
  (1042, '伊藤 国生'),
  (1043, '吉沢 弘泰'),
  (1044, '三輪 穂瑞'),
  (1045, '丸山 昭乃'),
  (1046, '川田 亮歩'),
  (1047, '山中 温史'),
  (1048, '大竹 悠馬'),
  (1049, '藤岡 一誠'),
  (1050, '中西 高貴'),
  (1051, '三島 夕莉'),
  (1052, '本多 宣宏'),
  (1053, '高松 敏寛'),
  (1054, '茂木 愛夏'),
  (1055, '伊藤 雅紀')
;

INSERT INTO affiliations(employee_id, department_id) VALUES
  (41, 100007),
  (42, 100005),
  (43, 100004),
  (44, 100007),
  (45, 100004),
  (46, 100007),
  (47, 100005),
  (48, 100007),
  (49, 100005),
  (50, 100007),
  (51, 100006),
  (52, 100005),
  (53, 100006),
  (54, 100004),
  (55, 100004)
;

