<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Wallet - EduOnline</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>body {
    min-height: 100vh;
    display: flex;
    flex-direction: column;
    background: #f0f2f5;
}

.wallet-wrap {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 40px 20px;
}

.wallet-card {
    background: #fff;
    border-radius: 20px;
    padding: 40px 38px;
    width: 100%;
    max-width: 460px;
    box-shadow: 0 8px 32px rgba(0,0,0,0.10);
    border: 1px solid #e8ecf5;
}

.wallet-header { text-align: center; margin-bottom: 28px; }

.wallet-icon {
    font-size: 48px;
    margin-bottom: 10px;
    display: block;
}

.wallet-card h2 {
    font-size: 22px; font-weight: 800;
    color: #1a1a2e; margin-bottom: 4px;
}

.wallet-sub {
    font-size: 13px; color: #6b7280; margin: 0;
}

.balance-box {
    background: linear-gradient(135deg, #4f46e5, #7c3aed);
    border-radius: 16px;
    padding: 28px;
    text-align: center;
    margin-bottom: 28px;
    box-shadow: 0 6px 20px rgba(79,70,229,0.30);
}

.balance-label {
    font-size: 12px; font-weight: 600;
    color: rgba(255,255,255,0.75);
    text-transform: uppercase; letter-spacing: 0.8px;
    margin-bottom: 8px;
}

.balance-amount {
    font-size: 32px; font-weight: 900;
    color: #fff; letter-spacing: 0.5px;
}

.topup-section h3 {
    font-size: 15px; font-weight: 700;
    color: #1a1a2e; margin-bottom: 12px;
}

.quick-amounts {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 8px;
    margin-bottom: 14px;
}

.quick-btn {
    padding: 10px;
    border: 1.5px solid #e0e4ef;
    border-radius: 10px;
    background: #f8f9ff;
    color: #4f46e5;
    font-size: 13px;
    font-weight: 700;
    cursor: pointer;
    transition: all 0.2s;
    font-family: inherit;
}

.quick-btn:hover {
    background: #4f46e5;
    color: #fff;
    border-color: #4f46e5;
}

.topup-input-wrap {
    position: relative;
    margin-bottom: 12px;
}

.topup-input-wrap input {
    width: 100%;
    padding: 11px 60px 11px 14px;
    border: 1.5px solid #e0e4ef;
    border-radius: 10px;
    font-size: 14px;
    color: #222;
    background: #fafafa;
    outline: none;
    font-family: inherit;
    box-sizing: border-box;
    transition: border-color 0.2s, box-shadow 0.2s;
}

.topup-input-wrap input:focus {
    border-color: #6366f1;
    box-shadow: 0 0 0 3px rgba(99,102,241,0.13);
    background: #fff;
}

.currency-label {
    position: absolute;
    right: 14px; top: 50%;
    transform: translateY(-50%);
    font-size: 12px; font-weight: 700;
    color: #9ca3af;
}

.btn-topup {
    width: 100%; padding: 13px;
    background: linear-gradient(135deg, #10b981, #059669);
    color: #fff; border: none; border-radius: 10px;
    font-size: 15px; font-weight: 700; cursor: pointer;
    font-family: inherit;
    transition: opacity 0.2s, transform 0.1s;
}

.btn-topup:hover { opacity: 0.90; transform: translateY(-1px); }

.btn-back {
    display: block; text-align: center;
    margin-top: 16px; font-size: 13px;
    color: #6b7280; text-decoration: none;
}

.btn-back:hover { color: #4f46e5; text-decoration: none; }</style>
</head>
<body>
<%@ include file="/header.jsp" %>

<div class="wallet-wrap">
    <div class="wallet-card">
        <div class="wallet-header">
            <div class="wallet-icon">&#128179;</div>
            <h2>My Wallet</h2>
            <p class="wallet-sub">Manage your balance</p>
        </div>

        <div class="balance-box">
            <div class="balance-label">Current Balance</div>
            <div class="balance-amount">
                <fmt:formatNumber value="${balance}"
                    type="number" groupingUsed="true"/> VND
            </div>
        </div>

        <c:if test="${param.success == '1'}">
            <div class="alert-success" style="margin-bottom:16px;">
                &#10003; Top-up successful!
            </div>
        </c:if>
        <c:if test="${param.error == '1'}">
            <div class="alert-error" style="margin-bottom:16px;">
                &#9888; Invalid amount. Please try again.
            </div>
        </c:if>

        <div class="topup-section">
            <h3>Top Up Balance</h3>
            <div class="quick-amounts">
                <button class="quick-btn" onclick="setAmount(100000)">100,000</button>
                <button class="quick-btn" onclick="setAmount(200000)">200,000</button>
                <button class="quick-btn" onclick="setAmount(500000)">500,000</button>
                <button class="quick-btn" onclick="setAmount(1000000)">1,000,000</button>
                <button class="quick-btn" onclick="setAmount(2000000)">2,000,000</button>
                <button class="quick-btn" onclick="setAmount(5000000)">5,000,000</button>
            </div>
            <form method="post"
                  action="${pageContext.request.contextPath}/wallet">
                <div class="topup-input-wrap">
                    <input type="number" name="amount" id="amountInput"
                           placeholder="Enter amount (VND)"
                           min="10000" step="10000" required/>
                    <span class="currency-label">VND</span>
                </div>
                <button type="submit" class="btn-topup">
                    &#43; Top Up Now
                </button>
            </form>
        </div>

        <a href="${pageContext.request.contextPath}/myCourses"
           class="btn-back">&#8592; Back to My Courses</a>
    </div>
</div>

<script>
    function setAmount(val) {
        document.getElementById('amountInput').value = val;
    }
</script>

<%@ include file="/footer.jsp" %>
</body>
</html>