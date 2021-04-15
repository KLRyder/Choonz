let tryLogin = () =>{
    let username = document.getElementById("Username").value;
    let password = document.getElementById("password").value;
    fetch(apiURL + 'sessions/login', {
        method: 'post',
        headers: {
            "Content-type": "application/json"
        },
        body: JSON.stringify({
            "username": username,
            "password": password
        })
    }).then(res => console.log(res))
        .then(() => {console.log("login")
            window.location.href = "/";
        })
        .catch((error) => console.error(error));
}