let tryCreate = () =>{
    let username = document.getElementById("Username").value;
    let password = document.getElementById("password").value;
    fetch(apiURL + 'sessions', {
        method: 'post',
        headers: {
            "Content-type": "application/json"
        },
        body: JSON.stringify({
            "username": username,
            "password": password,
            "role": "ADMIN"
        })
    }).then(res => console.log(res))
        .then(() => {location.href="/"
        })
        .catch((error) => console.error(error));
}