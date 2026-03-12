<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>${course.name} - EduOnline</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
        <style>
            .detail-hero {
                background: linear-gradient(135deg, #0d0d1a 0%, #1a1a3e 50%, #0d0d1a 100%);
                border: 1px solid rgba(255,255,255,0.08);
                border-radius: 16px;
                padding: 36px;
                margin-bottom: 28px;
                display: flex;
                gap: 32px;
                align-items: flex-start;
            }
            .detail-hero-thumb {
                width: 320px;
                min-width: 320px;
                border-radius: 12px;
                overflow: hidden;
                box-shadow: 0 8px 32px rgba(0,0,0,0.4);
            }
            .detail-hero-thumb img {
                width: 100%;
                height: 200px;
                object-fit: cover;
                display: block;
            }
            .detail-hero-thumb-placeholder {
                width: 100%;
                height: 200px;
                background: linear-gradient(135deg, #4f46e5, #7c3aed);
                display: flex;
                align-items: center;
                justify-content: center;
                font-size: 64px;
            }
            .detail-hero-info {
                flex: 1;
            }
            .detail-hero-info h1 {
                font-size: 26px;
                font-weight: 800;
                color: #f1f5f9;
                margin: 0 0 12px 0;
                line-height: 1.3;
            }

            .detail-hero-info .desc {
                color: #94a3b8;
                font-size: 15px;
                line-height: 1.6;
                margin-bottom: 20px;
            }
            .detail-meta-grid {
                display: grid;
                grid-template-columns: 1fr 1fr;
                gap: 10px;
                margin-bottom: 20px;
            }
            .detail-meta-item {
                background: rgba(255,255,255,0.06);
                border: 1px solid rgba(255,255,255,0.08);
                border-radius: 8px;
                padding: 10px 14px;
                font-size: 13px;
                color: #e2e8f0;
            }

            .detail-meta-item span {
                color: #6b7280;
                font-size: 11px;
                display: block;
                margin-bottom: 2px;
            }
            .detail-price {
                font-size: 28px;
                font-weight: 800;
                color: #fbbf24;
                margin-bottom: 20px;
            }
            .detail-section {
                background: #1a1a2e;
                border: 1px solid rgba(255,255,255,0.08);
                border-radius: 14px;
                padding: 28px;
                margin-bottom: 20px;
            }

            .detail-section h3 {
                font-size: 16px;
                font-weight: 700;
                color: #a5b4fc;
                text-transform: uppercase;
                letter-spacing: 1px;
                margin: 0 0 16px 0;
                padding-bottom: 10px;
                border-bottom: 1px solid rgba(255,255,255,0.08);
            }

            .detail-section p {
                color: #cbd5e1;
                line-height: 1.8;
                white-space: pre-line;
                font-size: 14px;
            }
            .video-wrap {
                position: relative;
                padding-bottom: 56.25%;
                height: 0;
                border-radius: 12px;
                overflow: hidden;
            }
            .video-wrap iframe {
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                border: none;
            }
            .video-locked {
                position: relative;
                padding-bottom: 56.25%;
                height: 0;
                border-radius: 12px;
                overflow: hidden;
                background: linear-gradient(135deg, #1e1b4b, #312e81);
            }
            .video-locked-inner {
                position: absolute;
                inset: 0;
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: center;
                gap: 12px;
                color: #a5b4fc;
            }
            .video-locked-inner .lock-icon {
                font-size: 48px;
            }
            .video-locked-inner p {
                font-size: 14px;
                color: #6b7280;
            }
            .action-box {
                background: rgba(255,255,255,0.04);
                border: 1px solid rgba(99,102,241,0.25);
                border-radius: 14px;
                padding: 24px;
                text-align: center;
            }
            .slot-badge {
                display: inline-block;
                padding: 4px 12px;
                border-radius: 999px;
                font-size: 12px;
                font-weight: 700;
                margin-bottom: 16px;
            }
            @media (max-width: 768px) {
                .detail-hero {
                    flex-direction: column;
                }
                .detail-hero-thumb {
                    width: 100%;
                    min-width: unset;
                }
                .detail-meta-grid {
                    grid-template-columns: 1fr;
                }
            }
        </style>
    </head>
    <body>
        <%@ include file="/header.jsp" %>

        <div class="container">

            <div style="margin-bottom:16px;">
                <a href="${pageContext.request.contextPath}/courseList"
                   style="color:#6b7280; font-size:13px; text-decoration:none;">
                    &#8592; Back to Courses
                </a>
            </div>

            <!-- ═══ HERO ═══ -->
            <div class="detail-hero">
                <div class="detail-hero-thumb">
                    <c:choose>
                        <c:when test="${not empty course.thumbnailUrl}">
                            <img src="${course.thumbnailUrl}" alt="${course.name}"
                                 onerror="this.style.display='none';
                                 this.nextElementSibling.style.display='flex';"/>
                            <div class="detail-hero-thumb-placeholder" style="display:none;">&#127891;</div>
                        </c:when>
                        <c:otherwise>
                            <div class="detail-hero-thumb-placeholder">&#127891;</div>
                        </c:otherwise>
                    </c:choose>
                </div>

                <div class="detail-hero-info">
                    <h1>${course.name}</h1>
                    <p class="desc">${course.description}</p>

                    <div class="detail-meta-grid">
                        <div class="detail-meta-item">
                            <span>&#128100; Instructor</span>
                            ${course.instructorName}
                            <c:if test="${course.instructorStatus == 'LOCKED'}">
                                <span class="badge badge-locked" style="font-size:10px;">Locked</span>
                            </c:if>
                        </div>
                        <div class="detail-meta-item">
                            <span>&#128197; Schedule</span>
                            ${course.scheduleText}
                        </div>
                        <div class="detail-meta-item">
                            <span>&#128198; Duration</span>
                            ${course.startDate} → ${course.endDate}
                        </div>
                        <div class="detail-meta-item">
                            <span>&#128101; Enrolled</span>
                            ${course.enrolledCount} / ${course.totalSlots} slots
                        </div>
                    </div>

                    <div class="detail-price">
                        <c:choose>
                            <c:when test="${course.tuitionFee == 0}">
                                🎁 Free
                            </c:when>
                            <c:otherwise>
                                &#128176; <fmt:formatNumber value="${course.tuitionFee}"
                                                  type="number" groupingUsed="true"/> VND
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <!-- Action Box -->
                    <div class="action-box">
                        <c:choose>
                            <%-- Slot --%>
                            <c:when test="${course.enrolledCount >= course.totalSlots}">
                                <span class="slot-badge" style="background:rgba(239,68,68,0.2); color:#f87171;">
                                    &#128683; Class Full
                                </span>
                            </c:when>
                            <c:when test="${course.totalSlots - course.enrolledCount <= 5}">
                                <span class="slot-badge" style="background:rgba(245,158,11,0.2); color:#fbbf24;">
                                    &#9888; Only ${course.totalSlots - course.enrolledCount} slots left!
                                </span>
                            </c:when>
                            <c:otherwise>
                                <span class="slot-badge" style="background:rgba(16,185,129,0.2); color:#34d399;">
                                    &#10003; ${course.totalSlots - course.enrolledCount} slots available
                                </span>
                            </c:otherwise>
                        </c:choose>

                        <%-- STUDENT logic --%>
                        <c:if test="${sessionScope.role == 'STUDENT'}">
                            <c:choose>
                                <c:when test="${myReg.status == 'APPROVED'}">
                                    <a href="${pageContext.request.contextPath}/student/learn?courseId=${course.courseId}"
                                       class="btn-approve"
                                       style="display:block; padding:12px; font-size:15px;
                                       background:linear-gradient(135deg,#10b981,#059669);">
                                        &#9654; Start Learning
                                    </a>
                                </c:when>
                                <c:when test="${myReg.status == 'PENDING' and myReg.paymentStatus == 'UNPAID'}">
                                    <div style="color:#fbbf24; margin-bottom:12px; font-size:13px;">
                                        &#9203; Registered — Payment required
                                    </div>
                                    <a href="${pageContext.request.contextPath}/payment?regId=${myReg.regId}"
                                       class="btn-approve"
                                       style="display:block; padding:12px;
                                       background:linear-gradient(135deg,#f59e0b,#d97706);">
                                        &#128179; Pay Now
                                    </a>
                                </c:when>
                                <c:when test="${myReg.status == 'PENDING' and myReg.paymentStatus == 'PAID'}">
                                    <div style="color:#a5b4fc; font-size:13px;">
                                        &#9203; Awaiting instructor approval
                                    </div>
                                </c:when>
                                <c:when test="${myReg.status == 'REJECTED'}">
                                    <div style="color:#f87171; margin-bottom:12px; font-size:13px;">
                                        &#10007; Your registration was rejected
                                        <c:if test="${myReg.paymentStatus == 'REFUNDED'}">
                                            — balance refunded
                                        </c:if>
                                    </div>
                                    <c:if test="${course.enrolledCount < course.totalSlots}">
                                        <form action="${pageContext.request.contextPath}/student/enroll" method="post">
                                            <input type="hidden" name="courseId" value="${course.courseId}"/>
                                            <button type="submit" class="btn-approve" style="width:100%; padding:12px;">
                                                &#128203; Enroll Again
                                            </button>
                                        </form>
                                    </c:if>
                                </c:when>
                                <c:otherwise>
                                    <%-- Chưa enroll --%>
                                    <c:choose>
                                        <c:when test="${course.enrolledCount >= course.totalSlots}">
                                            <button class="btn-reject" disabled style="width:100%; padding:12px;">
                                                &#128683; Class Full
                                            </button>
                                        </c:when>
                                        <c:otherwise>
                                            <form action="${pageContext.request.contextPath}/student/enroll" method="post">
                                                <input type="hidden" name="courseId" value="${course.courseId}"/>
                                                <button type="submit" class="btn-approve"
                                                        style="width:100%; padding:12px; font-size:15px;">
                                                    &#127891; Enroll Now
                                                </button>
                                            </form>
                                        </c:otherwise>
                                    </c:choose>
                                </c:otherwise>
                            </c:choose>
                        </c:if>

                        <%-- GUEST --%>
                        <c:if test="${empty sessionScope.user}">
                            <a href="${pageContext.request.contextPath}/login"
                               class="btn-approve" style="display:block; padding:12px; font-size:15px;">
                                &#128274; Login to Enroll
                            </a>
                        </c:if>

                        <%-- INSTRUCTOR / ADMIN --%>
                        <c:if test="${sessionScope.role == 'ADMIN'}">
                            <a href="${pageContext.request.contextPath}/admin/editCourse?courseId=${course.courseId}"
                               class="btn-secondary" style="display:block; padding:12px;">
                                &#9999; Edit Course
                            </a>
                        </c:if>
                        <c:if test="${sessionScope.role == 'INSTRUCTOR'}">
                            <a href="${pageContext.request.contextPath}/instructor/edit-course?courseId=${course.courseId}"
                               class="btn-secondary" style="display:block; padding:12px;">
                                &#9999; Edit Course
                            </a>
                        </c:if>
                    </div>
                </div>
            </div>

            <!-- ═══ VIDEO ═══ -->
            <c:if test="${not empty course.videoUrl}">
                <div class="detail-section">
                    <h3>&#127916; Preview Video</h3>
                    <c:choose>
                        <c:when test="${myReg.status == 'APPROVED' or sessionScope.role == 'INSTRUCTOR'
                                        or sessionScope.role == 'ADMIN'}">
                                <div class="video-wrap">
                                    <iframe src="${course.videoUrl}"
                                            allow="accelerometer; autoplay; clipboard-write;
                                            encrypted-media; gyroscope; picture-in-picture"
                                            allowfullscreen></iframe>
                                </div>
                        </c:when>
                        <c:otherwise>
                            <div class="video-locked">
                                <div class="video-locked-inner">
                                    <div class="lock-icon">&#128274;</div>
                                    <strong style="color:#e0e7ff;">Enroll to watch this video</strong>
                                    <p>Complete enrollment and payment to unlock full course content</p>
                                </div>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:if>

            <!-- ═══ DETAIL DESCRIPTION ═══ -->
            <c:if test="${not empty course.detailDescription}">
                <div class="detail-section">
                    <h3>&#128218; Course Details</h3>
                    <p>${course.detailDescription}</p>
                </div>
            </c:if>

        </div>

        <%@ include file="/footer.jsp" %>
    </body>
</html>