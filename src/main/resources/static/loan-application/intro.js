document.addEventListener('DOMContentLoaded', function () {
    const token = sessionStorage.getItem("token"); // Retrieve token

    if (!token) {
        alert("You are not logged in. Redirecting to home page...");
        window.location.href = "../index.html"; // Redirect to login page
        return;
    }

    function checkToken() {
        const currentToken = sessionStorage.getItem("token");
        if (!currentToken) {
            alert("Your session has expired. Redirecting to login page...");
            window.location.href = "../index.html"; // Redirect to login page
        }
    }

    const tokenCheckInterval = setInterval(checkToken, 500);

    window.addEventListener('beforeunload', function () {
        clearInterval(tokenCheckInterval);
    });

    const signOutLink = document.getElementById('signOutLink');


    signOutLink.addEventListener('click', function (event) {
        event.preventDefault(); // Prevent default link behavior
        alert("you logout you will get direct to homepage")
        sessionStorage.removeItem("token"); // Remove the token
        window.location.href = "../index.html"; // Redirect to home page or login page
    });

})

    // Fetch personal data from the server



