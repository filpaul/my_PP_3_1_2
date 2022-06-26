const tableBodyUser = document.querySelector('#tableBodyUser')

fetch(urlUserAuth)
    .then(res => res.json())
    .then(data => {
        let roles = getRoleForUser(data)
        let output = `
            <tr>
                <td> ${data.id} </td>
                <td> ${data.name} </td>
                <td> ${data.surName} </td>
                <td> ${data.age} </td>
                <td> ${data.email} </td>
                <td> ${roles}</td>
            </tr>`
        tableBodyUser.innerHTML = output
    })


