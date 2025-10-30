document.addEventListener("DOMContentLoaded", () => {
  const forms = document.querySelectorAll("form[action*='/platillos/eliminar']");

  forms.forEach(form => {
    form.addEventListener("submit", e => {
      e.preventDefault(); // Evita eliminación directa
      mostrarConfirmacion(form);
    });
  });
});

function mostrarConfirmacion(form) {
  // Evitar crear múltiples modales
  if (document.querySelector(".modal-confirmacion")) return;

  const modal = document.createElement("div");
  modal.className = "modal-confirmacion activo";

  modal.innerHTML = `
    <div class="modal-contenido">
      <h3>¿Eliminar platillo?</h3>
      <p>Esta acción no se puede deshacer.</p>
      <div class="modal-botones">
        <button class="btn-si">Sí, eliminar</button>
        <button class="btn-no">Cancelar</button>
      </div>
    </div>
  `;

  document.body.appendChild(modal);

  // Botones
  modal.querySelector(".btn-si").addEventListener("click", () => {
    modal.remove();
    form.submit();
  });

  modal.querySelector(".btn-no").addEventListener("click", () => {
    modal.classList.remove("activo");
    setTimeout(() => modal.remove(), 300); // animación suave al cerrar
  });
}
