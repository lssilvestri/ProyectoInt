const $modalError = document.getElementById("errorModal");

function validateInput(input) {
    const sqlRegex = /(\b(SELECT|UPDATE|INSERT INTO|DELETE FROM|DROP TABLE)\b)|(--)|(;)/i;

    if (input === "" && input.length === 0 && input === null && input === undefined) {
        const title = "🟡 Alerta";
        const text = `Por favor, completa todos los campos.`;

        alertModal(title, text);

        return null;
    }

    if (sqlRegex.test(input)) {
        const title = "🟡 Alerta";
        const text = `Se detectó una posible consulta SQL maliciosa. Por favor, evita incluir palabras clave SQL.`;

        alertModal(title, text);

        return null;
    }

    return input;
}

function validateDocument(document) {
    document = validateInput(document);
    if (!document) return null;

    document = document.trim().replace(/\s/g, "");
    const regex = /^\d{4,}$/;

    if (!regex.test(document)) {
        const title = "🟡 Alerta";
        const text = "El documento debe de tener más de 4 dígitos.";

        alertModal(title, text);
        return null;
    }

    return document;
}

function validatePassword(password) {
    password = validateInput(password);
    const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/;

    if (!regex.test(password)) {
        const title = "🟡 Alerta";
        const text = `La contraseña debe tener al menos 8 caracteres y contener al menos una letra minúscula, una letra mayúscula y un número.`;

        alertModal(title, text);

        return null;
    }

    return password;
}

function validatePhoneNumber(phoneNumber) {
    phoneNumber = validateInput(phoneNumber);

    // Remover cualquier caracter que no sea número
    phoneNumber = phoneNumber.replace(/\D/g, "");

    // Verificar si la longitud del número es válida
    if (phoneNumber.length < 10 || phoneNumber.length > 15) {
        const title = "🟡 Alerta";
        const text = `Por favor, coloca un número de teléfono válido.`;
        alertModal(title, text);
        return null;
    }

    // Formatear el número al estilo (322) 419-8413
    phoneNumber = phoneNumber.replace(/(\d{3})(\d{3})(\d{4})/, "($1) $2-$3");

    return phoneNumber;
}

function validateInputId(input, validIds) {
    input = validateInput(input);

    if (!validIds.some((id) => String(id) === input)) {
        const title = "🟡 Alerta";
        const text = `El La opción proporcionada no es válida.`;
        alertModal(title, text);
        return null;
    }

    return input;
}

function checkPasswordMatch(password, confirmPassword) {
    if (password !== confirmPassword) {
        const title = "🟡 Alerta";
        const text = `Las contraseñas deben de ser iguales.`;

        alertModal(title, text);

        return false;
    } else {
        return true;
    }
}

function alertModal(title, text, redirect) {
    const $modaltitle = $modalError.querySelector("#errorModalTitle");
    const $modaltext = $modalError.querySelector("#errorModalText");
    const $modalRedirect = $modalError.querySelector("#errorModalRedirect");
    const $modalConfirm = $modalError.querySelector("#errorModalConfirm");
    $modalConfirm.style.display = "none";

    if (redirect) {
        $modalRedirect.style.display = "block";
        $modalRedirect.href = redirect.href;
        $modalRedirect.textContent = redirect.text;
    } else {
        $modalRedirect.style.display = "none";
    }

    $modaltitle.innerHTML = title;
    $modaltext.innerHTML = text;
    $("#errorModal").modal("show");
}

function warningModal(title, text, btnValue) {
    const $modaltitle = $modalError.querySelector("#errorModalTitle");
    const $modaltext = $modalError.querySelector("#errorModalText");
    const $modalRedirect = $modalError.querySelector("#errorModalRedirect");
    const $modalConfirm = $modalError.querySelector("#errorModalConfirm");
    $modalRedirect.style.display = "none";

    if (btnValue) {
        $modalConfirm.value = btnValue;
    }

    $modaltitle.innerHTML = title;
    $modaltext.innerHTML = text;
    $("#errorModal").modal("show");
}

async function fetchData(url, method, messages, data = null) {
    try {
        const { success, failure } = messages;

        const myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");

        const options = {
            method: method,
            headers: myHeaders,
            body: method !== "GET" ? data : null,
            redirect: "follow",
        };

        const res = await fetch(url, options);

        if (!res.ok) {
            title = failure.title;
            text = `${failure.text}</br></br> Error en la petición.`;

            alertModal(title, text);
        } else {
            const response = await res.json();
            console.log("Response:", response);

            title = success.title;
            text = `${success.text}</br></br> Petición exitosa.`;
            redirect = success.redirect ? success.redirect : null;

            alertModal(title, text, redirect);
        }
    } catch (error) {
        console.log("Error:", error);

        title = failure.title;
        text = `${failure.text}</br></br> El servicio a retornado: <strong>${error.message}</strong>`;

        alertModal(title, text);
    }
}
