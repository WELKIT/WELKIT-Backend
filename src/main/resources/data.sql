-- 일반 유저
INSERT INTO users (id, email, email_type, google_email, password, job_role, is_company_verified, lock_pin, user_type)
VALUES
    (1, 'user1@test.com', 'PERSONAL_EMAIL', NULL, '$2b$12$q.7l.68OKUiiWwN/TSc2iuNgix7LhwcgWaBN8UNOgQupQ/ZLDWsY6', 'PLANNING_STRATEGY', false, NULL, 'USER'),
    (2, 'user2@test.com', 'PERSONAL_EMAIL', NULL, '$2b$12$q.7l.68OKUiiWwN/TSc2iuNgix7LhwcgWaBN8UNOgQupQ/ZLDWsY6', 'AI_DEVELOP_DATA', false, NULL, 'USER'),
    (3, 'user3@test.com', 'PERSONAL_EMAIL', NULL, '$2b$12$q.7l.68OKUiiWwN/TSc2iuNgix7LhwcgWaBN8UNOgQupQ/ZLDWsY6', 'DESIGN', false, NULL, 'USER'),
    (4, 'user4@test.com', 'PERSONAL_EMAIL', NULL, '$2b$12$q.7l.68OKUiiWwN/TSc2iuNgix7LhwcgWaBN8UNOgQupQ/ZLDWsY6', 'MARKETING_AD_MD', false, NULL, 'USER'),
    (5, 'user5@test.com', 'PERSONAL_EMAIL', NULL, '$2b$12$q.7l.68OKUiiWwN/TSc2iuNgix7LhwcgWaBN8UNOgQupQ/ZLDWsY6', 'EDUCATION', false, NULL, 'USER'),
    (6, 'user6@test.com', 'COMPANY_EMAIL', NULL, '$2b$12$q.7l.68OKUiiWwN/TSc2iuNgix7LhwcgWaBN8UNOgQupQ/ZLDWsY6', 'SALES', true, NULL, 'USER'),
    (7, 'user7@test.com', 'COMPANY_EMAIL', NULL, '$2b$12$q.7l.68OKUiiWwN/TSc2iuNgix7LhwcgWaBN8UNOgQupQ/ZLDWsY6', 'CUSTOMER_SUPPORT_TM', true, NULL, 'USER'),
    (8, 'user8@test.com', 'COMPANY_EMAIL', NULL, '$2b$12$q.7l.68OKUiiWwN/TSc2iuNgix7LhwcgWaBN8UNOgQupQ/ZLDWsY6', 'ACCOUNTING_TAX', true, NULL, 'USER'),
    (9, 'user9@test.com', 'COMPANY_EMAIL', NULL, '$2b$12$q.7l.68OKUiiWwN/TSc2iuNgix7LhwcgWaBN8UNOgQupQ/ZLDWsY6', 'MEDIA_CULTURE_SPORTS', true, NULL, 'USER'),
    (10, 'user10@test.com', 'COMPANY_EMAIL', NULL, '$2b$12$q.7l.68OKUiiWwN/TSc2iuNgix7LhwcgWaBN8UNOgQupQ/ZLDWsY6', 'FOOD_BEVERAGE', true, NULL, 'USER');

-- 관리자
INSERT INTO users (id, email, email_type, google_email, password, job_role, is_company_verified, lock_pin, user_type)
VALUES
    (11, 'admin1@test.com', 'PERSONAL_EMAIL', NULL, '$2b$12$q.7l.68OKUiiWwN/TSc2iuNgix7LhwcgWaBN8UNOgQupQ/ZLDWsY6', 'HR', true, NULL, 'ADMIN'),
    (12, 'admin2@test.com', 'PERSONAL_EMAIL', NULL, '$2b$12$q.7l.68OKUiiWwN/TSc2iuNgix7LhwcgWaBN8UNOgQupQ/ZLDWsY6', 'LEGAL_ADMIN_GENERAL', true, NULL, 'ADMIN'),
    (13, 'admin3@test.com', 'PERSONAL_EMAIL', NULL, '$2b$12$q.7l.68OKUiiWwN/TSc2iuNgix7LhwcgWaBN8UNOgQupQ/ZLDWsY6', 'LOGISTICS_FOREIGN_TRADE', true, NULL, 'ADMIN'),
    (14, 'admin4@test.com', 'PERSONAL_EMAIL', NULL, '$2b$12$q.7l.68OKUiiWwN/TSc2iuNgix7LhwcgWaBN8UNOgQupQ/ZLDWsY6', 'DRIVING_TRANSPORT_DELIVERY', true, NULL, 'ADMIN'),
    (15, 'admin5@test.com', 'PERSONAL_EMAIL', NULL, '$2b$12$q.7l.68OKUiiWwN/TSc2iuNgix7LhwcgWaBN8UNOgQupQ/ZLDWsY6', 'FINANCE_INSURANCE', true, NULL, 'ADMIN'),
    (16, 'admin6@test.com', 'PERSONAL_EMAIL', NULL, '$2b$12$q.7l.68OKUiiWwN/TSc2iuNgix7LhwcgWaBN8UNOgQupQ/ZLDWsY6', 'CUSTOMER_SERVICE_RETAIL', false, NULL, 'ADMIN'),
    (17, 'admin7@test.com', 'PERSONAL_EMAIL', NULL, '$2b$12$q.7l.68OKUiiWwN/TSc2iuNgix7LhwcgWaBN8UNOgQupQ/ZLDWsY6', 'ENGINEERING_DESIGN', false, NULL, 'ADMIN'),
    (18, 'admin8@test.com', 'PERSONAL_EMAIL', NULL, '$2b$12$q.7l.68OKUiiWwN/TSc2iuNgix7LhwcgWaBN8UNOgQupQ/ZLDWsY6', 'MANUFACTURING_PRODUCTION', false, NULL, 'ADMIN'),
    (19, 'admin9@test.com', 'PERSONAL_EMAIL', NULL, '$2b$12$q.7l.68OKUiiWwN/TSc2iuNgix7LhwcgWaBN8UNOgQupQ/ZLDWsY6', 'ARCHITECTURE_FACILITY', false, NULL, 'ADMIN'),
    (20, 'admin10@test.com', 'PERSONAL_EMAIL', NULL, '$2b$12$q.7l.68OKUiiWwN/TSc2iuNgix7LhwcgWaBN8UNOgQupQ/ZLDWsY6', 'MEDICAL_BIO', false, NULL, 'ADMIN');

INSERT INTO term_categories (id, name, user_id)
VALUES
    (1, 'Project Management', 1),
    (2, 'KPI', 1),
    (3, 'Roadmap', 1),
    (4, 'Stakeholder', 1),
    (5, 'Workflow', 1),

    (6, 'Machine Learning', 2),
    (7, 'Data Processing', 2),
    (8, 'Modeling', 2),
    (9, 'Validation', 2),
    (10, 'Prediction', 2),

    (11, 'Design Thinking', 3),
    (12, 'UI/UX', 3),
    (13, 'Typography', 3),
    (14, 'Interaction', 3),
    (15, 'User Research', 3),

    (16, 'SEO', 4),
    (17, 'Content', 4),
    (18, 'Branding', 4),
    (19, 'Campaign', 4),
    (20, 'Analytics', 4),

    (21, 'Curriculum', 5),
    (22, 'Assessment', 5),
    (23, 'Pedagogy', 5),
    (24, 'Lesson Plan', 5),
    (25, 'E-Learning', 5),

    (26, 'CRM', 6),
    (27, 'Lead', 6),
    (28, 'Pipeline', 6),
    (29, 'Account', 6),
    (30, 'Forecast', 6),

    (31, 'Call Center', 7),
    (32, 'Ticketing', 7),
    (33, 'SLA', 7),
    (34, 'Response', 7),
    (35, 'Retention', 7),

    (36, 'Accounting', 8),
    (37, 'Tax', 8),
    (38, 'Ledger', 8),
    (39, 'Financial Statement', 8),
    (40, 'Expense', 8),

    (41, 'Media Planning', 9),
    (42, 'Content Strategy', 9),
    (43, 'PR', 9),
    (44, 'Campaign', 9),
    (45, 'KPI Analysis', 9),

    (46, 'Food Safety', 10),
    (47, 'Menu Planning', 10),
    (48, 'Beverage', 10),
    (49, 'Inventory', 10),
    (50, 'Innovation', 10);

INSERT INTO terms (name, definition, category_id, user_id)
VALUES
    ('Agile', '반복적인 개발과 지속적인 개선을 중시하는 프로젝트 관리 방법', 1, 1),
    ('Sprint', '특정 기간 내에 완료해야 하는 작업 단위', 1, 1),
    ('Backlog', '완료해야 할 작업이나 기능의 목록', 2, 1),
    ('KPI', '성과를 측정하는 핵심 지표', 2, 1),
    ('Roadmap', '프로젝트 진행 계획', 3, 1),
    ('Milestone', '프로젝트 중 중요한 단계', 3, 1),
    ('Stakeholder', '프로젝트 이해관계자', 4, 1),
    ('Benchmark', '비교를 위한 기준', 4, 1),
    ('MVP', '필수 기능만 갖춘 최소 기능 제품', 5, 1),
    ('Workflow', '업무 진행 절차', 5, 1);

INSERT INTO terms (name, definition, category_id, user_id)
VALUES
    ('Machine Learning', '기계가 학습하도록 하는 알고리즘과 기술', 1, 2),
    ('Deep Learning', '인공신경망을 이용한 학습 방법', 1, 2),
    ('Data Pipeline', '데이터 처리 및 이동 과정', 2, 2),
    ('Feature Engineering', '모델 성능을 높이기 위한 변수 생성', 2, 2),
    ('Model Training', '학습 데이터를 사용하여 모델을 학습', 3, 2),
    ('Overfitting', '모델이 학습 데이터에 과적합 되는 현상', 3, 2),
    ('Cross Validation', '모델 검증을 위한 데이터 분할 방법', 4, 2),
    ('Hyperparameter', '모델 학습 전 설정하는 매개변수', 4, 2),
    ('Prediction', '모델을 통해 결과를 예측하는 과정', 5, 2),
    ('Accuracy', '정확도, 예측 성능 평가 지표', 5, 2);

INSERT INTO terms (name, definition, category_id, user_id)
VALUES
    ('Design Thinking', '사용자 중심 문제 해결 방법론', 1, 3),
    ('UI/UX', '사용자 인터페이스와 경험 설계', 1, 3),
    ('Wireframe', '화면 설계의 뼈대', 2, 3),
    ('Prototype', '시제품 또는 모형', 2, 3),
    ('Typography', '글자체와 글자 디자인', 3, 3),
    ('Color Theory', '색상 조합과 배색 원리', 3, 3),
    ('Interaction', '사용자와 시스템 간 상호작용', 4, 3),
    ('Responsive Design', '화면 크기에 맞춘 디자인', 4, 3),
    ('User Journey', '사용자 행동 흐름 분석', 5, 3),
    ('Persona', '대표 사용자 모델', 5, 3);

INSERT INTO terms (name, definition, category_id, user_id)
VALUES
    ('SEO', '검색엔진 최적화', 1, 4),
    ('Content Marketing', '콘텐츠를 통한 마케팅 전략', 1, 4),
    ('Branding', '브랜드 전략', 2, 4),
    ('Ad Campaign', '광고 캠페인 계획 및 실행', 2, 4),
    ('Market Research', '시장 조사', 3, 4),
    ('Consumer Insight', '소비자 인사이트 분석', 3, 4),
    ('Conversion Rate', '전환율', 4, 4),
    ('Engagement', '사용자 참여 지표', 4, 4),
    ('A/B Test', '두 가지 버전 테스트를 통한 검증', 5, 4),
    ('ROI', '투자 대비 수익', 5, 4);

INSERT INTO terms (name, definition, category_id, user_id)
VALUES
    ('Lecture Plan', '강의 계획 수립', 1, 5),
    ('Curriculum', '교육 과정 설계', 1, 5),
    ('Assessment', '학습 평가 방법', 2, 5),
    ('Learning Outcome', '학습 성과', 2, 5),
    ('Pedagogy', '교육학, 교수법', 3, 5),
    ('Syllabus', '강의 개요', 3, 5),
    ('Student Engagement', '학생 참여 활동', 4, 5),
    ('Lesson Plan', '수업 계획안', 4, 5),
    ('E-Learning', '온라인 학습 시스템', 5, 5),
    ('Workshop', '실습형 학습 프로그램', 5, 5);

INSERT INTO terms (name, definition, category_id, user_id)
VALUES
    ('CRM', '고객 관계 관리 시스템', 1, 6),
    ('Lead Management', '잠재 고객 관리', 1, 6),
    ('Sales Funnel', '판매 프로세스 단계', 2, 6),
    ('Prospecting', '영업 대상 탐색', 2, 6),
    ('Quota', '판매 목표', 3, 6),
    ('Pipeline', '영업 진행 과정', 3, 6),
    ('Cold Call', '전화 영업', 4, 6),
    ('Account Management', '고객 계정 관리', 4, 6),
    ('Closing', '계약 성사 과정', 5, 6),
    ('Forecast', '매출 예측', 5, 6);

INSERT INTO terms (name, definition, category_id, user_id)
VALUES
    ('Call Center', '고객 문의 응대 센터', 1, 7),
    ('Ticketing', '문의 티켓 관리 시스템', 1, 7),
    ('Script', '고객 응대 스크립트', 2, 7),
    ('SLA', '서비스 수준 계약', 2, 7),
    ('Support Workflow', '고객 지원 프로세스', 3, 7),
    ('Response Time', '응답 시간 관리', 3, 7),
    ('CRM Tools', '고객 관리 도구', 4, 7),
    ('Customer Feedback', '고객 피드백 분석', 4, 7),
    ('Retention', '고객 유지 전략', 5, 7),
    ('NPS', '고객 만족도 지표', 5, 7);

INSERT INTO terms (name, definition, category_id, user_id)
VALUES
    ('Accounting Principles', '회계 원칙', 1, 8),
    ('Tax Compliance', '세무 준수', 1, 8),
    ('Ledger', '원장', 2, 8),
    ('Balance Sheet', '재무상태표', 2, 8),
    ('Profit & Loss', '손익계산서', 3, 8),
    ('Cash Flow', '현금 흐름', 3, 8),
    ('Audit', '감사 과정', 4, 8),
    ('Financial Statement', '재무제표 분석', 4, 8),
    ('Tax Filing', '세금 신고 절차', 5, 8),
    ('Expense Management', '경비 관리', 5, 8);

INSERT INTO terms (name, definition, category_id, user_id)
VALUES
    ('Media Planning', '미디어 기획', 1, 9),
    ('Content Strategy', '콘텐츠 전략', 1, 9),
    ('PR', '홍보 활동', 2, 9),
    ('Campaign', '캠페인 실행', 2, 9),
    ('Audience Analysis', '청중 분석', 3, 9),
    ('Social Media', '소셜 미디어 관리', 3, 9),
    ('Engagement Metrics', '참여 지표 분석', 4, 9),
    ('Brand Awareness', '브랜드 인지도 측정', 4, 9),
    ('Media Buying', '광고 구매 과정', 5, 9),
    ('KPI Analysis', '성과 분석 지표', 5, 9);

INSERT INTO terms (name, definition, category_id, user_id)
VALUES
    ('Food Safety', '식품 안전 관리', 1, 10),
    ('Menu Planning', '메뉴 계획', 1, 10),
    ('Beverage Management', '음료 관리', 2, 10),
    ('Inventory Control', '재고 관리', 2, 10),
    ('Kitchen Hygiene', '주방 위생 관리', 3, 10),
    ('Recipe Development', '레시피 개발 과정', 3, 10),
    ('Customer Satisfaction', '고객 만족도 관리', 4, 10),
    ('Service Quality', '서비스 품질 평가', 4, 10),
    ('Food Costing', '식재료 비용 계산', 5, 10),
    ('Culinary Innovation', '조리 혁신 및 메뉴 개발', 5, 10);

INSERT INTO insight_cards (title, description, note, type, is_favorite, user_id)
VALUES
    ('이수민 전략 기획자', '기업 전략 수립 및 실행 계획 관리', '신규 사업 전략 기획 경험 다수', 'PERSON', true, 1),
    ('김현우 프로젝트 매니저', '팀 프로젝트 일정 및 업무 조율', '애자일 기반 프로젝트 관리 경험', 'PERSON', false, 1),
    ('박지연 비즈니스 분석가', '시장 조사 및 경쟁사 분석', 'SWOT 분석 기반 전략 수립', 'PERSON', false, 1),
    ('정우성 전략 컨설턴트', '기업 전략 컨설팅 및 프로젝트 수행', '전략 보고서 작성 경험 포함', 'PERSON', true, 1),
    ('최민수 사업 개발 담당자', '신규 사업 아이디어 발굴 및 기획', '사업성 분석 및 기획 경험', 'PERSON', false, 1),
    ('고객 만족 분석 보고서', '고객 피드백 수집 및 만족도 분석', 'Survey 및 NPS 활용', 'WORK', true, 1),
    ('시장 경쟁 분석', '주요 경쟁사 동향 분석 및 보고', '시장 점유율, 가격 전략 분석 포함', 'WORK', false, 1),
    ('신규 서비스 론칭 계획', '서비스 출시 일정 및 단계별 계획 수립', 'MVP 정의 및 목표 설정 포함', 'WORK', false, 1),
    ('팀 성과 분석', '팀별 KPI 분석 및 개선안 도출', '월간 리포트 작성 경험', 'WORK', true, 1),
    ('업무 프로세스 최적화', '사내 업무 효율화 방안 설계', '업무 자동화 및 프로세스 개선', 'WORK', false, 1);

INSERT INTO insight_cards (title, description, note, type, is_favorite, user_id)
VALUES
    ('이현준 데이터 엔지니어', '대규모 데이터 파이프라인 설계 및 운영', 'Hadoop, Spark, Airflow 기반 데이터 처리 경험.', 'PERSON', true, 2),
    ('최민수 머신러닝 엔지니어', '모델 학습 및 최적화 경험', 'Python, TensorFlow 활용', 'PERSON', false, 2),
    ('박지훈 데이터 분석가', '분석 요구 사항 수집 및 결과 해석', '비즈니스 이해 기반 분석 경험', 'PERSON', false, 2),
    ('김하늘 AI 연구원', '최신 AI 기술 연구 및 모델 구현', 'Transformer, CNN, RNN 경험', 'PERSON', true, 2),
    ('정다은 데이터 사이언티스트', '데이터 기반 의사결정 지원', '통계 분석, Python, SQL 경험', 'PERSON', false, 2),
    ('AI 모델 성능 분석 보고서', '모델 성능 평가 및 개선안 도출', 'Accuracy, F1 score 기반 분석', 'WORK', true, 2),
    ('데이터 전처리 자동화', '데이터 정제 및 ETL 자동화', 'Python, SQL, Airflow 활용', 'WORK', false, 2),
    ('추천 시스템 개발', '사용자 행동 기반 추천 모델 구현', 'Collaborative Filtering 적용', 'WORK', false, 2),
    ('데이터 시각화 대시보드', '주요 지표 시각화 및 모니터링', 'Tableau, Plotly 활용', 'WORK', true, 2),
    ('모델 하이퍼파라미터 튜닝', '최적 모델 파라미터 탐색', 'Grid Search, Random Search 경험', 'WORK', false, 2);

INSERT INTO insight_cards (title, description, note, type, is_favorite, user_id)
VALUES
    ('김서연 UX 디자이너', '사용자 경험 설계 및 개선', 'UI/UX 설계, Figma 사용 경험', 'PERSON', true, 3),
    ('이준호 UI 디자이너', '웹/앱 화면 디자인 및 프로토타이핑', 'Sketch, Adobe XD 활용', 'PERSON', false, 3),
    ('박민지 그래픽 디자이너', '시각 디자인 및 브랜드 콘텐츠 제작', 'Illustrator, Photoshop 활용', 'PERSON', false, 3),
    ('최성훈 인터랙션 디자이너', 'UI 인터랙션 설계 및 사용자 테스트', 'Animation, Prototype 경험 포함', 'PERSON', true, 3),
    ('정하은 제품 디자이너', '서비스 및 제품 디자인 설계', 'Figma, Wireframe 작성 경험', 'PERSON', false, 3),
    ('서비스 화면 개선 프로젝트', '사용자 피드백 기반 UI 개선', 'AB 테스트 및 화면 설계 경험 포함', 'WORK', true, 3),
    ('브랜드 아이덴티티 디자인', '기업 브랜드 디자인 전략 수립', '로고, 컬러, 폰트 디자인 포함', 'WORK', false, 3),
    ('모바일 앱 리디자인', '앱 UI/UX 전면 리디자인', '사용자 흐름 분석 및 화면 구성', 'WORK', false, 3),
    ('프로토타입 제작', '신규 기능 시제품 제작 및 테스트', 'Figma, InVision 사용 경험', 'WORK', true, 3),
    ('사용자 여정 맵 분석', '사용자 행동 패턴 분석', 'Persona 및 Journey Map 작성', 'WORK', false, 3);

INSERT INTO insight_cards (title, description, note, type, is_favorite, user_id)
VALUES
    ('이유진 마케팅 전략가', '광고 캠페인 및 브랜드 전략 수립', '디지털 마케팅, SNS 분석 경험', 'PERSON', true, 4),
    ('김도현 콘텐츠 마케터', '콘텐츠 기획 및 제작', '블로그, SNS 채널 관리 경험', 'PERSON', false, 4),
    ('박세은 브랜드 매니저', '브랜드 이미지 관리 및 홍보', '브랜드 캠페인 운영 경험', 'PERSON', false, 4),
    ('정민재 디지털 마케터', '온라인 광고 및 캠페인 관리', 'Google Ads, Meta Ads 활용 경험', 'PERSON', true, 4),
    ('최유진 광고 기획자', '광고 소재 기획 및 운영', '캠페인 성과 분석 포함', 'PERSON', false, 4),
    ('SNS 분석 보고서', '광고 및 콘텐츠 효과 분석', 'Engagement, CTR 분석 포함', 'WORK', true, 4),
    ('브랜드 캠페인 실행', '광고 캠페인 진행 및 결과 분석', 'A/B 테스트 수행 경험', 'WORK', false, 4),
    ('시장 조사 리포트', '시장 트렌드 및 경쟁사 분석', '리서치 기반 전략 수립 포함', 'WORK', false, 4),
    ('광고 ROI 분석', '투자 대비 광고 성과 측정', '캠페인별 ROI 계산 경험', 'WORK', true, 4),
    ('마케팅 자동화 프로젝트', '마케팅 자동화 툴 적용', 'CRM 연동 및 이메일 캠페인 경험', 'WORK', false, 4);

INSERT INTO insight_cards (title, description, note, type, is_favorite, user_id)
VALUES
    ('김하나 교육 기획자', '강의 계획 수립 및 교육 과정 설계', '대학 강의 및 워크숍 경험', 'PERSON', true, 5),
    ('이민호 교수', '교육 커리큘럼 개발 및 강의 진행', '교재 제작 및 수업 진행 경험', 'PERSON', false, 5),
    ('박지훈 학습 컨설턴트', '학습 평가 및 성과 분석', 'Assessment 설계 경험 포함', 'PERSON', false, 5),
    ('정예린 교육 연구원', '교육 방법론 연구 및 적용', 'Pedagogy 연구 경험', 'PERSON', true, 5),
    ('최수진 강사', '학생 참여 활동 설계', '워크숍 및 실습형 수업 경험', 'PERSON', false, 5),
    ('학습 성과 분석 보고서', '학생 학습 결과 분석 및 개선안 도출', '시험, 퀴즈, 출석 데이터 활용', 'WORK', true, 5),
    ('커리큘럼 개선 프로젝트', '교육 과정 최적화 및 개선', '강의 피드백 기반 설계', 'WORK', false, 5),
    ('온라인 학습 플랫폼 운영', 'E-Learning 콘텐츠 제작 및 관리', 'LMS 운영 경험 포함', 'WORK', false, 5),
    ('워크숍 진행 보고서', '실습형 학습 프로그램 운영 기록', '참가자 피드백 및 개선안', 'WORK', true, 5),
    ('강의 계획서 작성', '강의 목표 및 세부 계획 수립', 'Syllabus 기반 작성 경험', 'WORK', false, 5);

INSERT INTO insight_cards (title, description, note, type, is_favorite, user_id)
VALUES
    ('김성현 영업 매니저', '영업 전략 수립 및 팀 관리', '판매 데이터 기반 목표 설정 경험', 'PERSON', true, 6),
    ('이준영 세일즈 엔지니어', '고객 맞춤 솔루션 제공 및 기술 지원', '제품 데모 및 기술 상담 경험', 'PERSON', false, 6),
    ('박지민 계정 관리자', '고객 계정 및 관계 관리', 'CRM 활용 경험 포함', 'PERSON', false, 6),
    ('정다윤 영업 전략가', '영업 계획 및 프로세스 설계', '세일즈 파이프라인 관리 경험', 'PERSON', true, 6),
    ('최현우 영업 분석가', '영업 데이터 분석 및 개선안 제안', '월간 매출 보고서 작성 경험', 'PERSON', false, 6),
    ('판매 목표 분석 보고서', '영업 실적 및 목표 달성 분석', 'KPI 기반 성과 보고', 'WORK', true, 6),
    ('잠재 고객 관리 프로젝트', '리드 발굴 및 Follow-up 관리', 'CRM 및 이메일 캠페인 활용', 'WORK', false, 6),
    ('영업 파이프라인 개선', '영업 단계 최적화 및 효율화', 'Pipeline 시각화 및 분석 포함', 'WORK', false, 6),
    ('계약 성사 사례 분석', '성과 높은 계약 사례 정리', '성공 요인 분석 및 공유', 'WORK', true, 6),
    ('콜드콜 캠페인 실행', '신규 고객 대상 전화 영업 진행', '대상 리스트 관리 및 스크립트 작성', 'WORK', false, 6);

INSERT INTO insight_cards (title, description, note, type, is_favorite, user_id)
VALUES
    ('이채린 고객 지원 담당자', '고객 문의 응대 및 문제 해결', 'Call Center 운영 경험', 'PERSON', true, 7),
    ('박준형 고객 서비스 매니저', '고객 지원 프로세스 관리', 'SLA 준수 경험 포함', 'PERSON', false, 7),
    ('김서윤 지원 분석가', '고객 피드백 분석 및 개선안 제시', 'NPS 분석 경험', 'PERSON', false, 7),
    ('정민호 고객 경험 전문가', '고객 경험 전략 설계', 'CRM 및 지원 데이터 기반 경험', 'PERSON', true, 7),
    ('최유진 고객 대응 코치', '고객 상담 및 응대 교육', '스크립트 제작 및 교육 경험', 'PERSON', false, 7),
    ('고객 만족도 분석 보고서', 'CS 지표 및 개선안 도출', '응답 시간, NPS, Ticket 분석', 'WORK', true, 7),
    ('지원 프로세스 개선 프로젝트', '업무 효율화 및 자동화 설계', 'Support Workflow 개선 경험', 'WORK', false, 7),
    ('티켓 관리 시스템 최적화', '문의 티켓 처리 효율화', 'Priority 관리 및 SLA 적용 경험', 'WORK', false, 7),
    ('고객 피드백 보고서 작성', '고객 의견 수집 및 개선 방안 도출', 'Survey 데이터 분석 포함', 'WORK', true, 7),
    ('CRM 툴 도입 프로젝트', '고객 관리 시스템 도입 및 운영', '툴 비교 및 사용자 교육 경험', 'WORK', false, 7);

INSERT INTO insight_cards (title, description, note, type, is_favorite, user_id)
VALUES
    ('김동현 회계사', '회계 및 재무 관리', '재무제표 분석 및 보고 경험', 'PERSON', true, 8),
    ('이수진 세무사', '세무 신고 및 준수 관리', '법인세, 소득세 신고 경험', 'PERSON', false, 8),
    ('박현우 회계 분석가', '회계 데이터 분석 및 개선', 'Ledger, Balance Sheet 검토 경험', 'PERSON', false, 8),
    ('정예린 재무 컨설턴트', '재무 전략 및 예산 수립', 'Cash Flow 관리 경험', 'PERSON', true, 8),
    ('최민호 감사 전문가', '내부/외부 감사 수행', 'Audit 준비 및 재무 검토 경험', 'PERSON', false, 8),
    ('재무제표 분석 보고서', '회계 및 재무 상태 분석', 'Profit & Loss, Balance Sheet 분석', 'WORK', true, 8),
    ('세금 신고 프로젝트', '세무 신고 절차 관리 및 검토', 'Tax Filing 경험 포함', 'WORK', false, 8),
    ('경비 관리 프로세스 개선', 'Expense 관리 및 효율화', '자동화 및 보고 체계 개선', 'WORK', false, 8),
    ('회계 원칙 교육 자료 제작', '회계 기준 및 규정 교육 자료 개발', '내부 직원 교육용 문서 포함', 'WORK', true, 8),
    ('Tax Compliance 점검', '세무 준수 점검 및 보고', '정기 점검 및 문제 발견 경험', 'WORK', false, 8);

INSERT INTO insight_cards (title, description, note, type, is_favorite, user_id)
VALUES
    ('김도현 미디어 전략가', '미디어 기획 및 콘텐츠 전략 수립', '채널별 콘텐츠 최적화 경험', 'PERSON', true, 9),
    ('이유진 PR 전문가', '홍보 활동 및 언론 대응', '보도자료 작성 및 배포 경험', 'PERSON', false, 9),
    ('박서현 콘텐츠 매니저', '콘텐츠 제작 및 관리', '블로그, SNS 채널 운영 경험', 'PERSON', false, 9),
    ('정민재 마케팅 분석가', '데이터 기반 캠페인 분석', 'Engagement, KPI 분석 경험', 'PERSON', true, 9),
    ('최유진 미디어 플래너', '광고 매체 기획 및 집행', 'Media Buying 경험 포함', 'PERSON', false, 9),
    ('청중 분석 보고서', 'Audience 및 참여 지표 분석', '타겟별 데이터 분석 포함', 'WORK', true, 9),
    ('브랜드 인지도 조사', '시장 내 브랜드 Awareness 측정', '설문 조사 및 데이터 분석 경험', 'WORK', false, 9),
    ('캠페인 실행 보고서', '광고 및 PR 캠페인 진행 기록', '성과 분석 및 개선안 포함', 'WORK', false, 9),
    ('콘텐츠 전략 문서', '콘텐츠 기획 및 전략 정리', '채널별 콘텐츠 계획 포함', 'WORK', true, 9),
    ('KPI 분석 보고서', '성과 지표 측정 및 개선안 제안', '분석 도구 및 데이터 활용 경험', 'WORK', false, 9);

INSERT INTO insight_cards (title, description, note, type, is_favorite, user_id)
VALUES
    ('이서윤 셰프', '조리 및 메뉴 개발', '레시피 개발 및 주방 운영 경험', 'PERSON', true, 10),
    ('김민재 식음료 매니저', '음식 및 음료 관리', 'Inventory, Food Costing 경험 포함', 'PERSON', false, 10),
    ('박지연 F&B 컨설턴트', '식음료 전략 및 품질 관리', '식품 안전 및 메뉴 개발 경험', 'PERSON', false, 10),
    ('정하은 주방 위생 전문가', '주방 위생 관리 및 안전 점검', 'HACCP 관리 경험 포함', 'PERSON', true, 10),
    ('최민수 메뉴 플래너', '메뉴 기획 및 레시피 개발', '신규 메뉴 출시 경험 포함', 'PERSON', false, 10),
    ('식품 안전 점검 보고서', '주방 및 식품 안전 점검 기록', 'Food Safety Audit 경험', 'WORK', true, 10),
    ('메뉴 계획 프로젝트', '주간/월간 메뉴 기획', '재료 비용 및 계절별 메뉴 포함', 'WORK', false, 10),
    ('고객 만족도 조사', '식음료 서비스 만족도 분석', 'Survey 기반 데이터 분석 경험', 'WORK', false, 10),
    ('조리 혁신 보고서', '신규 조리법 개발 및 적용', '레시피 테스트 및 개선 경험', 'WORK', true, 10),
    ('재료 비용 분석', '식재료 비용 및 효율 관리', 'Food Costing 분석 포함', 'WORK', false, 10);

INSERT INTO retrospectives (title, content, type, start_date, end_date, user_id)
VALUES
    ('주간 전략 회고 1', '이번 주 전략 기획 업무 정리 및 개선점 도출', 'WEEKLY', '2025-09-01', '2025-09-07', 1),
    ('주간 전략 회고 2', '팀 미팅 및 프로젝트 진행 상황 점검', 'WEEKLY', '2025-09-08', '2025-09-14', 1),
    ('주간 전략 회고 3', '시장 분석 및 경쟁사 동향 평가', 'WEEKLY', '2025-09-15', '2025-09-21', 1),
    ('주간 전략 회고 4', '프로젝트 로드맵 업데이트 및 조정', 'WEEKLY', '2025-09-22', '2025-09-28', 1),
    ('주간 전략 회고 5', '팀원 업무 분배 및 효율 개선 점검', 'WEEKLY', '2025-09-29', '2025-10-05', 1),
    ('월간 전략 회고 1', '이번 달 주요 전략 계획 검토 및 개선안 도출', 'MONTHLY', '2025-08-01', '2025-08-31', 1),
    ('월간 전략 회고 2', '팀 성과 분석 및 KPI 달성 점검', 'MONTHLY', '2025-09-01', '2025-09-30', 1),
    ('월간 전략 회고 3', '프로젝트 일정 조정 및 리스크 관리', 'MONTHLY', '2025-07-01', '2025-07-31', 1),
    ('월간 전략 회고 4', '팀원 역량 평가 및 교육 계획 수립', 'MONTHLY', '2025-06-01', '2025-06-30', 1),
    ('월간 전략 회고 5', '분기별 목표 점검 및 차기 계획 수립', 'MONTHLY', '2025-05-01', '2025-05-31', 1);

INSERT INTO retrospectives (title, content, type, start_date, end_date, user_id)
VALUES
    ('주간 AI 모델 개발 회고 1', '이번 주 모델 학습 및 데이터 전처리 진행 상황', 'WEEKLY', '2025-09-01', '2025-09-07', 2),
    ('주간 AI 모델 개발 회고 2', 'Feature Engineering 및 하이퍼파라미터 튜닝 점검', 'WEEKLY', '2025-09-08', '2025-09-14', 2),
    ('주간 AI 모델 개발 회고 3', '모델 검증 결과 분석 및 개선안 도출', 'WEEKLY', '2025-09-15', '2025-09-21', 2),
    ('주간 AI 모델 개발 회고 4', '데이터 파이프라인 최적화 및 오류 수정', 'WEEKLY', '2025-09-22', '2025-09-28', 2),
    ('주간 AI 모델 개발 회고 5', '팀 코드 리뷰 및 성능 향상 논의', 'WEEKLY', '2025-09-29', '2025-10-05', 2),
    ('월간 AI 모델 개발 회고 1', '월간 모델 학습 성능 점검 및 보고', 'MONTHLY', '2025-08-01', '2025-08-31', 2),
    ('월간 AI 모델 개발 회고 2', '데이터 파이프라인 개선 및 운영 검토', 'MONTHLY', '2025-09-01', '2025-09-30', 2),
    ('월간 AI 모델 개발 회고 3', '팀 성과 및 프로젝트 진행율 평가', 'MONTHLY', '2025-07-01', '2025-07-31', 2),
    ('월간 AI 모델 개발 회고 4', '분석 결과 공유 및 차기 계획 수립', 'MONTHLY', '2025-06-01', '2025-06-30', 2),
    ('월간 AI 모델 개발 회고 5', '모델 배포 및 모니터링 결과 정리', 'MONTHLY', '2025-05-01', '2025-05-31', 2);

INSERT INTO retrospectives (title, content, type, start_date, end_date, user_id)
VALUES
    ('주간 UI/UX 회고 1', '이번 주 디자인 작업 및 프로토타입 점검', 'WEEKLY', '2025-09-01', '2025-09-07', 3),
    ('주간 UI/UX 회고 2', '사용자 피드백 기반 개선 사항 반영', 'WEEKLY', '2025-09-08', '2025-09-14', 3),
    ('주간 UI/UX 회고 3', '와이어프레임 검토 및 UI 요소 수정', 'WEEKLY', '2025-09-15', '2025-09-21', 3),
    ('주간 UI/UX 회고 4', '프로토타입 테스트 및 상호작용 개선', 'WEEKLY', '2025-09-22', '2025-09-28', 3),
    ('주간 UI/UX 회고 5', '색상 및 타이포그래피 조정', 'WEEKLY', '2025-09-29', '2025-10-05', 3),
    ('월간 디자인 회고 1', '월간 프로젝트 디자인 리뷰 및 개선안 도출', 'MONTHLY', '2025-08-01', '2025-08-31', 3),
    ('월간 디자인 회고 2', '사용자 여정 분석 및 UX 최적화 계획', 'MONTHLY', '2025-09-01', '2025-09-30', 3),
    ('월간 디자인 회고 3', '디자인 시스템 업데이트 및 적용 결과 점검', 'MONTHLY', '2025-07-01', '2025-07-31', 3),
    ('월간 디자인 회고 4', '팀원 디자인 리뷰 및 피드백 기록', 'MONTHLY', '2025-06-01', '2025-06-30', 3),
    ('월간 디자인 회고 5', '분기별 UI/UX 개선 사항 정리', 'MONTHLY', '2025-05-01', '2025-05-31', 3);

INSERT INTO retrospectives (title, content, type, start_date, end_date, user_id)
VALUES
    ('주간 마케팅 회고 1', '이번 주 광고 캠페인 성과 분석 및 개선점 도출', 'WEEKLY', '2025-09-01', '2025-09-07', 4),
    ('주간 마케팅 회고 2', '콘텐츠 마케팅 전략 회의 및 실행 결과 정리', 'WEEKLY', '2025-09-08', '2025-09-14', 4),
    ('주간 마케팅 회고 3', '시장 조사 결과 기반 신규 캠페인 아이디어 논의', 'WEEKLY', '2025-09-15', '2025-09-21', 4),
    ('주간 마케팅 회고 4', '소셜 미디어 참여율 점검 및 개선안 적용', 'WEEKLY', '2025-09-22', '2025-09-28', 4),
    ('주간 마케팅 회고 5', '광고 예산 사용 및 ROI 분석', 'WEEKLY', '2025-09-29', '2025-10-05', 4),
    ('월간 마케팅 회고 1', '월간 캠페인 성과 보고 및 전략 조정', 'MONTHLY', '2025-08-01', '2025-08-31', 4),
    ('월간 마케팅 회고 2', '브랜드 인지도 조사 결과 공유 및 대응 전략', 'MONTHLY', '2025-09-01', '2025-09-30', 4),
    ('월간 마케팅 회고 3', '콘텐츠 제작 및 배포 계획 검토', 'MONTHLY', '2025-07-01', '2025-07-31', 4),
    ('월간 마케팅 회고 4', '팀 성과 점검 및 KPI 달성 여부 확인', 'MONTHLY', '2025-06-01', '2025-06-30', 4),
    ('월간 마케팅 회고 5', '분기별 마케팅 전략 평가 및 차기 계획 수립', 'MONTHLY', '2025-05-01', '2025-05-31', 4);

INSERT INTO retrospectives (title, content, type, start_date, end_date, user_id)
VALUES
    ('주간 교육 회고 1', '강의 계획 수립 및 수업 진행 점검', 'WEEKLY', '2025-09-01', '2025-09-07', 5),
    ('주간 교육 회고 2', '학생 참여 및 학습 성과 확인', 'WEEKLY', '2025-09-08', '2025-09-14', 5),
    ('주간 교육 회고 3', '교육 콘텐츠 제작 및 개선 사항 정리', 'WEEKLY', '2025-09-15', '2025-09-21', 5),
    ('주간 교육 회고 4', '평가 방식 검토 및 학습 효과 분석', 'WEEKLY', '2025-09-22', '2025-09-28', 5),
    ('주간 교육 회고 5', '학생 피드백 반영 및 수업 계획 조정', 'WEEKLY', '2025-09-29', '2025-10-05', 5),
    ('월간 교육 회고 1', '월간 강의 성과 분석 및 개선 계획 수립', 'MONTHLY', '2025-08-01', '2025-08-31', 5),
    ('월간 교육 회고 2', '교육 과정 평가 및 커리큘럼 조정', 'MONTHLY', '2025-09-01', '2025-09-30', 5),
    ('월간 교육 회고 3', '팀원 강의 준비 상태 점검', 'MONTHLY', '2025-07-01', '2025-07-31', 5),
    ('월간 교육 회고 4', '분기별 학습 성과 평가 및 기록 정리', 'MONTHLY', '2025-06-01', '2025-06-30', 5),
    ('월간 교육 회고 5', '워크숍 및 실습형 프로그램 효과 분석', 'MONTHLY', '2025-05-01', '2025-05-31', 5);

INSERT INTO retrospectives (title, content, type, start_date, end_date, user_id)
VALUES
    ('주간 영업 회고 1', '이번 주 영업 목표 달성 현황 점검', 'WEEKLY', '2025-09-01', '2025-09-07', 6),
    ('주간 영업 회고 2', '잠재 고객 관리 및 리드 확보 상황 확인', 'WEEKLY', '2025-09-08', '2025-09-14', 6),
    ('주간 영업 회고 3', '판매 파이프라인 진행 상황 분석', 'WEEKLY', '2025-09-15', '2025-09-21', 6),
    ('주간 영업 회고 4', '계약 성사율 점검 및 개선 전략 논의', 'WEEKLY', '2025-09-22', '2025-09-28', 6),
    ('주간 영업 회고 5', '팀 미팅 및 영업 전략 조정', 'WEEKLY', '2025-09-29', '2025-10-05', 6),
    ('월간 영업 회고 1', '월간 매출 목표 달성 여부 분석', 'MONTHLY', '2025-08-01', '2025-08-31', 6),
    ('월간 영업 회고 2', '영업 파이프라인 운영 및 문제점 점검', 'MONTHLY', '2025-09-01', '2025-09-30', 6),
    ('월간 영업 회고 3', '팀원 성과 평가 및 피드백 기록', 'MONTHLY', '2025-07-01', '2025-07-31', 6),
    ('월간 영업 회고 4', '분기별 영업 전략 평가 및 개선안 도출', 'MONTHLY', '2025-06-01', '2025-06-30', 6),
    ('월간 영업 회고 5', '고객 관리 및 CRM 운영 성과 점검', 'MONTHLY', '2025-05-01', '2025-05-31', 6);

INSERT INTO retrospectives (title, content, type, start_date, end_date, user_id)
VALUES
    ('주간 고객 지원 회고 1', '이번 주 고객 문의 응대 및 처리 점검', 'WEEKLY', '2025-09-01', '2025-09-07', 7),
    ('주간 고객 지원 회고 2', '티켓 관리 시스템 운영 상황 확인', 'WEEKLY', '2025-09-08', '2025-09-14', 7),
    ('주간 고객 지원 회고 3', '고객 피드백 분석 및 대응 전략 수립', 'WEEKLY', '2025-09-15', '2025-09-21', 7),
    ('주간 고객 지원 회고 4', '서비스 수준 계약(SLA) 점검', 'WEEKLY', '2025-09-22', '2025-09-28', 7),
    ('주간 고객 지원 회고 5', '팀 미팅 및 지원 업무 효율화 논의', 'WEEKLY', '2025-09-29', '2025-10-05', 7),
    ('월간 고객 지원 회고 1', '월간 고객 만족도 분석 및 개선안 도출', 'MONTHLY', '2025-08-01', '2025-08-31', 7),
    ('월간 고객 지원 회고 2', '고객 유지 전략 및 NPS 점검', 'MONTHLY', '2025-09-01', '2025-09-30', 7),
    ('월간 고객 지원 회고 3', '팀 성과 평가 및 교육 계획 수립', 'MONTHLY', '2025-07-01', '2025-07-31', 7),
    ('월간 고객 지원 회고 4', '분기별 지원 프로세스 개선 사항 정리', 'MONTHLY', '2025-06-01', '2025-06-30', 7),
    ('월간 고객 지원 회고 5', '고객 서비스 퀄리티 점검 및 향후 계획', 'MONTHLY', '2025-05-01', '2025-05-31', 7);

INSERT INTO retrospectives (title, content, type, start_date, end_date, user_id)
VALUES
    ('주간 회계 회고 1', '이번 주 회계 처리 및 세무 업무 점검', 'WEEKLY', '2025-09-01', '2025-09-07', 8),
    ('주간 회계 회고 2', '원장 관리 및 재무 데이터 확인', 'WEEKLY', '2025-09-08', '2025-09-14', 8),
    ('주간 회계 회고 3', '재무상태표 및 손익계산서 검토', 'WEEKLY', '2025-09-15', '2025-09-21', 8),
    ('주간 회계 회고 4', '현금 흐름 분석 및 문제점 파악', 'WEEKLY', '2025-09-22', '2025-09-28', 8),
    ('주간 회계 회고 5', '세금 신고 준비 및 확인', 'WEEKLY', '2025-09-29', '2025-10-05', 8),
    ('월간 회계 회고 1', '월간 재무 성과 분석 및 개선안 도출', 'MONTHLY', '2025-08-01', '2025-08-31', 8),
    ('월간 회계 회고 2', '회계 감사 준비 및 결과 점검', 'MONTHLY', '2025-09-01', '2025-09-30', 8),
    ('월간 회계 회고 3', '팀 성과 평가 및 업무 효율 개선 계획', 'MONTHLY', '2025-07-01', '2025-07-31', 8),
    ('월간 회계 회고 4', '분기별 재무 보고 및 전략 검토', 'MONTHLY', '2025-06-01', '2025-06-30', 8),
    ('월간 회계 회고 5', '세무 신고 마감 및 기록 점검', 'MONTHLY', '2025-05-01', '2025-05-31', 8);

INSERT INTO retrospectives (title, content, type, start_date, end_date, user_id)
VALUES
    ('주간 미디어 회고 1', '이번 주 콘텐츠 전략 및 미디어 기획 점검', 'WEEKLY', '2025-09-01', '2025-09-07', 9),
    ('주간 미디어 회고 2', 'PR 활동 및 캠페인 진행 상황 점검', 'WEEKLY', '2025-09-08', '2025-09-14', 9),
    ('주간 미디어 회고 3', '청중 분석 및 참여 지표 검토', 'WEEKLY', '2025-09-15', '2025-09-21', 9),
    ('주간 미디어 회고 4', '브랜드 인지도 향상 전략 논의', 'WEEKLY', '2025-09-22', '2025-09-28', 9),
    ('주간 미디어 회고 5', '광고 구매 및 KPI 분석', 'WEEKLY', '2025-09-29', '2025-10-05', 9),
    ('월간 미디어 회고 1', '월간 미디어 기획 성과 분석', 'MONTHLY', '2025-08-01', '2025-08-31', 9),
    ('월간 미디어 회고 2', '콘텐츠 전략 점검 및 개선안 도출', 'MONTHLY', '2025-09-01', '2025-09-30', 9),
    ('월간 미디어 회고 3', '팀 성과 평가 및 피드백 정리', 'MONTHLY', '2025-07-01', '2025-07-31', 9),
    ('월간 미디어 회고 4', '분기별 캠페인 성과 점검', 'MONTHLY', '2025-06-01', '2025-06-30', 9),
    ('월간 미디어 회고 5', '청중 분석 결과 기반 전략 수정', 'MONTHLY', '2025-05-01', '2025-05-31', 9);

INSERT INTO retrospectives (title, content, type, start_date, end_date, user_id)
VALUES
    ('주간 식음료 회고 1', '이번 주 식품 안전 관리 및 메뉴 계획 점검', 'WEEKLY', '2025-09-01', '2025-09-07', 10),
    ('주간 식음료 회고 2', '재고 관리 및 음료 관리 상태 확인', 'WEEKLY', '2025-09-08', '2025-09-14', 10),
    ('주간 식음료 회고 3', '레시피 개발 및 조리 과정 점검', 'WEEKLY', '2025-09-15', '2025-09-21', 10),
    ('주간 식음료 회고 4', '주방 위생 및 서비스 품질 점검', 'WEEKLY', '2025-09-22', '2025-09-28', 10),
    ('주간 식음료 회고 5', '고객 만족도 분석 및 피드백 반영', 'WEEKLY', '2025-09-29', '2025-10-05', 10),
    ('월간 식음료 회고 1', '월간 매출 및 원가 분석', 'MONTHLY', '2025-08-01', '2025-08-31', 10),
    ('월간 식음료 회고 2', '분기별 메뉴 혁신 및 개선 계획', 'MONTHLY', '2025-09-01', '2025-09-30', 10),
    ('월간 식음료 회고 3', '팀 성과 평가 및 직원 교육 계획 수립', 'MONTHLY', '2025-07-01', '2025-07-31', 10),
    ('월간 식음료 회고 4', '분기별 식음료 품질 점검 및 개선 계획', 'MONTHLY', '2025-06-01', '2025-06-30', 10),
    ('월간 식음료 회고 5', '고객 피드백 기반 메뉴 개선 및 서비스 전략 수립', 'MONTHLY', '2025-05-01', '2025-05-31', 10);

