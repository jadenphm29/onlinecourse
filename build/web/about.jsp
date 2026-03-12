<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>About Us - EduOnline</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="header.jsp" %>

<div class="container">
    <h2>About EduOnline</h2>

    <!-- Hero -->
    <div class="about-hero">
        <div style="font-size:36px; margin-bottom:10px;">🎓</div>
        <h3>Chào mừng đến với EduOnline</h3>
        <p>Chúng tôi cung cấp các khóa học chất lượng cao về IT, ngoại ngữ,
           kỹ năng mềm và nhiều lĩnh vực khác — giúp bạn học mọi lúc, mọi nơi.</p>
    </div>

    <!-- Features -->
    <h3>✨ Tính năng nổi bật</h3>
    <div class="about-features-grid">
        <div class="about-feature-card">
            <div class="feature-icon">📚</div>
            <div class="feature-title">Khóa học đa dạng</div>
            <p>IT, ngoại ngữ, kỹ năng mềm và nhiều lĩnh vực khác.</p>
        </div>
        <div class="about-feature-card">
            <div class="feature-icon">🖥️</div>
            <div class="feature-title">Đăng ký trực tuyến</div>
            <p>Đăng ký khóa học dễ dàng chỉ với vài thao tác đơn giản.</p>
        </div>
        <div class="about-feature-card">
            <div class="feature-icon">📊</div>
            <div class="feature-title">Theo dõi tiến độ</div>
            <p>Xem trạng thái đăng ký và lịch sử học tập của bạn.</p>
        </div>
        <div class="about-feature-card">
            <div class="feature-icon">👨‍🏫</div>
            <div class="feature-title">Giảng viên chất lượng</div>
            <p>Học từ các giảng viên có kinh nghiệm thực tế.</p>
        </div>
    </div>

    <!-- Contact -->
    <h3>📬 Thông tin liên hệ</h3>
    <div class="form-card about-contact-card">
        <div class="contact-row">
            <div class="contact-icon" style="background:#eff6ff;">📧</div>
            <div>
                <div class="contact-label">Email</div>
                <div class="contact-value">support@eduonline.com</div>
            </div>
        </div>
        <div class="contact-row">
            <div class="contact-icon" style="background:#f0fdf4;">📞</div>
            <div>
                <div class="contact-label">Phone</div>
                <div class="contact-value">+84 123 456 789</div>
            </div>
        </div>
        <div class="contact-row">
            <div class="contact-icon" style="background:#fdf4ff;">📍</div>
            <div>
                <div class="contact-label">Address</div>
                <div class="contact-value">Ho Chi Minh City, Vietnam</div>
            </div>
        </div>
    </div>

    <div style="margin-top:24px;">
        <a href="${pageContext.request.contextPath}/courseList"
           class="btn-approve" style="padding:11px 24px; font-size:14px;">
            📚 Browse Courses →
        </a>
    </div>
</div>

<%@ include file="footer.jsp" %>
</body>
</html>