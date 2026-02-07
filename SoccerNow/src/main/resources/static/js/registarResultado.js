/* Funções javascript utilizadas na funcionalidade de registar resultado */

/* Adicionar campo de jogador */
function adicionarCampo(campo) {
    var div = document.getElementById(campo);
    var input = document.createElement("input");
    input.type = "number";
    input.name = campo;
    input.placeholder = "ID do " + (div.getElementsByTagName("input").length + 1) + "º Jogador";
    div.appendChild(input);
}

/* Remover campo */
function removerCampo(campo) {
    var div = document.getElementById(campo);
    var inputs = div.getElementsByTagName("input");
    if (inputs.length > 1) {  
        div.removeChild(inputs[inputs.length - 1]); 
    } else {
        alert("Não é possível remover o primeiro campo!");
    }
}

/* Adicionar campo de arbitro */
function adicionarArbitro(campo) {
    var div = document.getElementById(campo);
    var input = document.createElement("input");
    input.type = "number";
    input.name = campo;
    input.placeholder = "ID do " + (div.getElementsByTagName("input").length + 1) + "º Arbitro";
    div.appendChild(input);
}

/* Adicionar campo de jogador (maximo de campos) */
function adicionarJogador(containerId, maxPlayers) {
    let container = document.getElementById(containerId);
    let inputs = container.getElementsByTagName('input');
    if (inputs.length < maxPlayers) {
        const novoCampo = document.createElement('input');
        novoCampo.type = "number";
        novoCampo.name = containerId === 'IDEquipaCasa' ? "IDEquipaCasa" : "IDEquipaVisitante";
        novoCampo.placeholder = "ID do " + (inputs.length + 1) + "º Jogador";
        container.appendChild(novoCampo);
        container.appendChild(document.createElement("br"));
    } else {
        alert('Máximo de 5 jogadores atingido para esta equipa!');
    }
}
