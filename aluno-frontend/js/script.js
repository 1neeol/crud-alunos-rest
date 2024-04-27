$("#inputPhone").mask("(99) 99999-9999");

var alunos = [];
var cursos = [];

let selectedRadioButtonValue;

document
  .querySelectorAll('input[name="flexRadioDefault"]')
  .forEach((radioButton) => {
    radioButton.addEventListener("click", function () {
      if (this.checked) {
        const labelElement = document.querySelector(`label[for="${this.id}"]`);
        selectedRadioButtonValue = labelElement.textContent.trim();
        console.log(selectedRadioButtonValue);
      }
    });
  });

loadCursos();
loadAlunos();

function loadCursos() {
  $.ajax({
    url: "http://localhost:8080/cursos",
    type: "GET",
    async: false,
    success: (response) => {
      cursos = response;
      for (var curso of cursos) {
        document.getElementById(
          "selectCurso"
        ).innerHTML += `<option value=${curso.id}>${curso.nome}</option>`;
      }
    },
  });
}

function loadAlunos() {
  $.getJSON("http://localhost:8080/alunos", (response) => {
    alunos = response;
    for (let aluno of alunos) {
      addAlunoTable(aluno);
    }
  });
}

function save() {
  var aluno = {
    id: alunos.length + 1,
    nome: document.getElementById("inputName").value,
    email: document.getElementById("inputMail").value,
    telefone: document.getElementById("inputPhone").value,
    turno: selectedRadioButtonValue,
    curso: {
      id: null,
      nome: document.getElementById("selectCurso").value,
    },
  };

  const dataToJson = JSON.stringify(aluno);

  console.log("Json abaixo");
  console.log(dataToJson, "json");
  $.ajax({
    url: "http://localhost:8080/alunos",
    type: "POST",
    contentType: "application/json",
    data: dataToJson,
    success: (alunoSalvo) => {
      addAlunoTable(alunoSalvo);
      alunos.push(alunoSalvo);
      document.getElementById("formAluno");
    },
  });
}

function addAlunoTable(aluno) {
  var table = document.getElementById("alunosTable");

  var newRow = table.insertRow();

  var idAluno = document.createTextNode(aluno.id);
  newRow.insertCell().appendChild(idAluno);

  var nomeAluno = document.createTextNode(aluno.nome);
  newRow.insertCell().appendChild(nomeAluno);

  var emailAluno = document.createTextNode(aluno.email);
  var cell = newRow.insertCell();
  cell.className = "d-none d-md-table-cell";
  cell.appendChild(emailAluno);

  var telefoneAluno = document.createTextNode(aluno.telefone);
  newRow.insertCell().appendChild(telefoneAluno);

  var nomeCurso = document.createTextNode(aluno.curso.nome);
  newRow.insertCell().appendChild(nomeCurso);

  var turnoCurso = document.createTextNode(aluno.turno);
  newRow.insertCell().appendChild(turnoCurso);
}
