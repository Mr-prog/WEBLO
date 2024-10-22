const input = document.getElementById('submit-button')
const inputY = document.getElementById('input_y')
const inputR = document.getElementById('input_r')

document
    .querySelectorAll('.checkboxes input[type="checkbox"]')
    .forEach(checkbox => {
        checkbox.addEventListener('change', function () {
            if (this.checked) {
                document
                    .querySelectorAll('.checkboxes input[type="checkbox"]')
                    .forEach(cb => {
                        if (cb !== this) {
                            cb.checked = false
                        }
                    })
            }
        })
    })

input.addEventListener('click', function () {
    const x = document.querySelector('.checkboxes input:checked').value;
    const y = inputY.value;
    const r = inputR.value;
    fetch(`http://localhost:8080/fcgi-bin/lab1.jar?x=${x}&y=${y}&r=${r}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(response => response.json())
        .then(data => {
            const row = document.createElement('tr');
            row.innerHTML = `<td>${data.x}</td><td>${data.y}</td><td>${data.r}</td><td>${data.execution_time}</td><td>${new Date().toLocaleTimeString()}</td><td>${data.result}</td>`;
            document.getElementById('result_table').appendChild(row);
        })
})

inputY.addEventListener('input', function () {
    const value = parseFloat(this.value)
    if (isNaN(value) || value < -3 || value > 5) {
        this.value = ''
    }
})

inputR.addEventListener('input', function () {
    const value = parseFloat(this.value)
    if (isNaN(value) || value < 1 || value > 4) {
        this.value = ''
    }
})
