function checkAdminAccess() {
    const token = sessionStorage.getItem("token");

    if (!token) {
        alert("Unauthorized! Please log in.");
        window.location.href = "../account-sign-in-up/sign-in.html"; // Redirect to login
        return;
    }

    try {
        const tokenPayload = JSON.parse(atob(token.split(".")[1])); // Decode JWT
        if (tokenPayload.role !== "ADMIN") {
            alert("Access Denied! Admins only.");
            window.location.href = "../index.html"; // Redirect non-admins
        }
    } catch (error) {
        console.error("Error decoding token:", error);
        sessionStorage.removeItem("token");
        window.location.href = "login.html"; // Redirect to login
    }
}

function checkUserAccess() {
    const token = sessionStorage.getItem("token");

    if (!token) {
        alert("Unauthorized! Please log in.");
        window.location.href = "../index.html"; // Redirect to login
        return;
    }

    try {
        const tokenPayload = JSON.parse(atob(token.split(".")[1])); // Decode JWT
        if (tokenPayload.role !== "USER") {
            alert("Access Denied! USER only.");
            window.location.href = "index.html"; // Redirect non-admins
        }
    } catch (error) {
        console.error("Error decoding token:", error);
        sessionStorage.removeItem("token");
        window.location.href = "login.html"; // Redirect to login
    }
}

// Run this function on `admin.html`
if (["DetailReport.html", "expenseReport.html", "managerAdmin.html", "something.html"].some(page => window.location.pathname.includes(page))) {
    checkAdminAccess();
}


// Run this function on `user.html`
// if (["intro.html", "personal-info.html" ].some(page => window.location.pathname.includes(page))) {
//     checkUserAccess();
// }


document.addEventListener("DOMContentLoaded", function () {
    if (["intro.html", "personal-info.html"].some(page => window.location.pathname.includes(page))) {
        checkUserAccess();
    }
});