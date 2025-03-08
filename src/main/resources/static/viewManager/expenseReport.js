

// Fetch JSON data from the Servlet
var but = document.getElementById("showInfo")
but.addEventListener('click' , function (event) {
    event.preventDefault();
    console.log("get user expense")

    const reportDiv = document.getElementById('report');
    reportDiv.innerHTML = `<p style="font-size: 18px; font-weight: bold;">Fetching data... ⏳</p>`;

    fetch('http://localhost:8080/flash-credit/Borr\nowerServlet/getExpenseReport')
    .then(response => response.json())
    .then(data => {
        // const reportDiv = document.getElementById('report');

        // Display basic info
        reportDiv.innerHTML = `
            <h2>Expense Report</h2>
            <p><strong>ID:</strong> ${data.id}</p>
            <p><strong>From:</strong> ${data.fromMonth} <strong>To:</strong> ${data.toMonth}</p>
            <p><strong>Coverage Days:</strong> ${data.coverageDays}</p>
        `;

        // Display payments
        reportDiv.innerHTML += `<h3>Payments</h3>`;
        data.payments.forEach(payment => {
            reportDiv.innerHTML += `
                <p><strong>${payment.division}:</strong> ${payment.avgMonthly} (${payment.percentageTotal}%)</p>
            `;
        });

        // Display loan interests
        reportDiv.innerHTML += `<h3>Loan Interests</h3>`;
        data.loanInterests.changeHistory.forEach(history => {
            reportDiv.innerHTML += `
                <p><strong>${history.date}:</strong> ${history.amount}</p>
            `;
        });

        // Display loan repayments
        reportDiv.innerHTML += `<h3>Loan Repayments</h3>`;
        data.loanRepayments.changeHistory.forEach(history => {
            reportDiv.innerHTML += `
                <p><strong>${history.date}:</strong> ${history.amount}</p>
            `;
        });
    })
    .catch(error => {
        console.error('Error fetching data:', error);
    })


    })