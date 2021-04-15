'use strict'
const apiURL = 'http://localhost:8082/'
const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);

function displayLoginDetails() {
    /* Displays (if valid) the username, then either login or logout button*/
    let navbar = document.getElementById('rightNavbar');
    let logLink = document.getElementById('logNavLink');
    let finalItem = document.getElementById('finalNavListItem');

    let uname = 'guest';
    let li = document.createElement('li');
    fetch(apiURL + 'sessions').then(resp => resp.json())
        .then(data => {
            uname = data.username;

            li.innerHTML = uname;
            li.className = 'username';
            navbar.insertBefore(li, finalItem);
            logLink.innerText = "Logout";
            logLink.onclick = () => {
                fetch(apiURL + 'sessions/logout', {
                    method: 'post'
                })
            }
        })
        .catch(() => {
                //    put login here

                li.innerHTML = uname;
                li.className = 'username';
                navbar.insertBefore(li, finalItem);
                logLink.innerText = "Login";
            }
        )
}

displayLoginDetails();