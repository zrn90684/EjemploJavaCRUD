<section id="actions" class="py-4 mb-4 bg-light">
    <div class="container">
        <div class="row">
            <div class="col-md-3">
                <a href="/ControlClientes/index.jsp" class="btn btn-ligth btn-block">
                    <!-- data-toggle="modal" data-target="#agregarClienteModal" > -->
                    <i class="fas fa-arrow-left"></i> Regresar al inicio
                </a>
            </div>

            <div class="col-md-3">
                <button type="submit" class="btn btn-sucess btn-block">
                    <i class="fas fa-check"></i> Guardar cliente
                </button>
            </div>
            
            <!-- Boton para eliminar va por metodo get -->
            <div class="col-md-3">
                <a href="${pageContext.request.contextPath}/ServletControlador?accion=eliminar&idCliente=${cliente.idCliente}" 
                   class="btn btn-danger btn-block">
                    <!-- data-toggle="modal" data-target="#agregarClienteModal" > -->
                    <i class="fas fa-trash"></i> Eliminar cliente
                </a>
            </div>

        </div>
    </div>

</section>