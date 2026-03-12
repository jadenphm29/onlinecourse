<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>${course.name} - Learn</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
        <style>
            .learn-layout {
                display: grid;
                grid-template-columns: 1fr 340px;
                gap: 24px;
                align-items: start;
            }
            .learn-video-wrap {
                position: relative;
                padding-bottom: 56.25%;
                height: 0;
                border-radius: 14px;
                overflow: hidden;
                background: #000;
                box-shadow: 0 8px 32px rgba(0,0,0,0.5);
            }
            .learn-video-wrap iframe {
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                border: none;
            }
            .learn-video-placeholder {
                position: absolute;
                inset: 0;
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: center;
                background: linear-gradient(135deg, #1e1b4b, #312e81);
                color: #6b7280;
                gap: 12px;
            }
            .learn-title {
                font-size: 20px;
                font-weight: 800;
                color: #f1f5f9;
                margin: 16px 0 8px 0;
            }

            .learn-desc {
                color: #94a3b8;
                font-size: 14px;
                line-height: 1.6;
                margin-bottom: 16px;
            }

            .learn-sidebar {
                background: #1a1a2e;
                border: 1px solid rgba(255,255,255,0.10);
                border-radius: 14px;
                padding: 20px;
                position: sticky;
                top: 20px;
            }
            .learn-sidebar h3 {
                font-size: 14px;
                font-weight: 700;
                color: #a5b4fc;
                text-transform: uppercase;
                letter-spacing: 1px;
                margin: 0 0 16px 0;
                padding-bottom: 10px;
                border-bottom: 1px solid rgba(255,255,255,0.08);
            }
            .sidebar-item {
                display: flex;
                flex-direction: column;
                gap: 4px;
                padding: 12px 0;
                border-bottom: 1px solid rgba(255,255,255,0.06);
            }

            .sidebar-item:last-child {
                border-bottom: none;
            }

            .sidebar-item .label {
                font-size: 11px;
                color: #6b7280;
                text-transform: uppercase;
                letter-spacing: 0.5px;
            }

            .sidebar-item .value {
                font-size: 14px;
                color: #e2e8f0;
                font-weight: 600;
            }

            .approved-badge {
                display: inline-flex;
                align-items: center;
                gap: 6px;
                background: rgba(16,185,129,0.15);
                border: 1px solid rgba(16,185,129,0.3);
                color: #34d399;
                padding: 6px 14px;
                border-radius: 999px;
                font-size: 12px;
                font-weight: 700;
                margin-bottom: 16px;
            }
            .detail-content {
                background: #1a1a2e;
                border: 1px solid rgba(255,255,255,0.08);
                border-radius: 14px;
                padding: 24px;
                margin-top: 20px;
                color: #cbd5e1;
                font-size: 14px;
                line-height: 1.8;
                white-space: pre-line;
            }

            .detail-content h3 {
                font-size: 15px;
                font-weight: 700;
                color: #a5b4fc;
                text-transform: uppercase;
                letter-spacing: 1px;
                margin: 0 0 14px 0;
                padding-bottom: 10px;
                border-bottom: 1px solid rgba(255,255,255,0.08);
            }
            @media (max-width: 900px) {
                .learn-layout {
                    grid-template-columns: 1fr;
                }
                .learn-sidebar {
                    position: static;
                }
            }
        </style>
    </head>
    <body>
        <%@ include file="/header.jsp" %>

        <div class="container">

            <div style="margin-bottom:16px; display:flex; align-items:center; gap:16px;">
                <a href="${pageContext.request.contextPath}/myCourses"
                   style="color:#6b7280; font-size:13px; text-decoration:none;">
                    &#8592; My Courses
                </a>
                <span class="approved-badge">&#10003; Enrolled & Approved</span>
            </div>

            <div class="learn-layout">

                <!-- ═══ LEFT: Video + Info ═══ -->
                <div>
                    <%-- Video --%>
                    <div class="learn-video-wrap">
                        <c:choose>
                            <c:when test="${not empty course.videoUrl}">
                                <iframe src="${course.videoUrl}?rel=0&modestbranding=1"
                                        allow="accelerometer; autoplay; clipboard-write;
                                        encrypted-media; gyroscope; picture-in-picture"
                                        allowfullscreen></iframe>
                                </c:when>
                                <c:otherwise>
                                <div class="learn-video-placeholder">
                                    <div style="font-size:56px;">&#127916;</div>
                                    <p style="font-size:14px;">No video available for this course</p>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <h2 class="learn-title">${course.name}</h2>
                    <p class="learn-desc">${course.description}</p>

                    <%-- Detail Description --%>
                    <c:if test="${not empty course.detailDescription}">
                        <div class="detail-content">
                            <h3>&#128218; Course Details</h3>
                            ${course.detailDescription}
                        </div>
                    </c:if>
                </div>

                <!-- ═══ RIGHT: Sidebar ═══ -->
                <div class="learn-sidebar">
                    <h3>&#127979; Course Info</h3>

                    <div class="sidebar-item">
                        <span class="label">&#128100; Instructor</span>
                        <span class="value">${course.instructorName}</span>
                    </div>
                    <div class="sidebar-item">
                        <span class="label">&#128197; Schedule</span>
                        <span class="value">${course.scheduleText}</span>
                    </div>
                    <div class="sidebar-item">
                        <span class="label">&#128198; Start Date</span>
                        <span class="value">${course.startDate}</span>
                    </div>
                    <div class="sidebar-item">
                        <span class="label">&#128198; End Date</span>
                        <span class="value">${course.endDate}</span>
                    </div>
                    <div class="sidebar-item">
                        <span class="label">&#128176; Tuition Fee</span>
                        <span class="value" style="color:#fbbf24;">
                            <c:choose>
                                <c:when test="${course.tuitionFee == 0}">Free</c:when>
                                <c:otherwise>
                                    <fmt:formatNumber value="${course.tuitionFee}"
                                                      type="number" groupingUsed="true"/> VND
                                </c:otherwise>
                            </c:choose>
                        </span>
                    </div>
                    <div class="sidebar-item">
                        <span class="label">&#128101; Class Size</span>
                        <span class="value">${course.enrolledCount} / ${course.totalSlots}</span>
                    </div>
                    <div class="sidebar-item">
                        <span class="label">&#9989; Status</span>
                        <span class="value" style="color:#34d399;">Approved</span>
                    </div>

                    <div style="margin-top:20px;">
                        <a href="${pageContext.request.contextPath}/course?courseId=${course.courseId}"
                           class="btn-secondary"
                           style="display:block; text-align:center; padding:10px; font-size:13px;">
                            &#128196; Course Detail Page
                        </a>
                    </div>
                </div>

            </div>
        </div>

        <%@ include file="/footer.jsp" %>
    </body>
</html>