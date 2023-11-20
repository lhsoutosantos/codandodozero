public class Livro:
    def __init__(self, titulo, autor, isbn):
        self.titulo = titulo
        self.autor = autor
        self.isbn = isbn
        self.status = "Disponível"
        self.emprestado_para = None

    def emprestar(self, usuario):
        if self.status == "Disponível":
            self.status = "Emprestado"
            self.emprestado_para = usuario
            return f"{self.titulo} emprestado para {usuario.nome}."
        else:
            raise ValueError(f"{self.titulo} já está emprestado.")

    def devolver(self):
        if self.status == "Emprestado":
            self.status = "Disponível"
            usuario = self.emprestado_para
            self.emprestado_para = None
            return f"{self.titulo} devolvido por {usuario.nome}."
        else:
            raise ValueError(f"{self.titulo} não está emprestado.")


class Usuario:
    def __init__(self, nome, user_id):
        self.nome = nome
        self.user_id = user_id
        self.livros_emprestados = []

    def pegar_livro(self, livro):
        if livro.status == "Disponível":
            self.livros_emprestados.append(livro)
            return livro.emprestar(self)
        else:
            raise ValueError(f"{livro.titulo} não está disponível para empréstimo.")

    def retornar_livro(self, livro):
        if livro in self.livros_emprestados:
            self.livros_emprestados.remove(livro)
            return livro.devolver()
        else:
            raise ValueError(f"{livro.titulo} não foi emprestado para {self.nome}.")

class Biblioteca:
    def __init__(self):
        self.lista_livros = []
        self.lista_usuarios = []
        self.historico_emprestimos = []

    def adicionar_livro(self, livro):
        self.lista_livros.append(livro)

    def remover_livro(self, livro):
        if livro in self.lista_livros:
            self.lista_livros.remove(livro)
        else:
            raise ValueError(f"{livro.titulo} não está na biblioteca.")

    def adicionar_usuario(self, usuario):
        self.lista_usuarios.append(usuario)

    def remover_usuario(self, usuario):
        if usuario in self.lista_usuarios:
            self.lista_usuarios.remove(usuario)
        else:
            raise ValueError(f"{usuario.nome} não está registrado na biblioteca.")

    def registrar_emprestimo(self, livro, usuario):
        emprestimo = {"livro": livro.titulo, "usuario": usuario.nome}
        self.historico_emprestimos.append(emprestimo)


# Exemplo de uso:
livro1 = Livro("Python for Beginners", "John Smith", "123456789")
livro2 = Livro("Data Science 101", "Jane Doe", "987654321")

usuario1 = Usuario("Alice", 1)
usuario2 = Usuario("Bob", 2)

biblioteca = Biblioteca()
biblioteca.adicionar_livro(livro1)
biblioteca.adicionar_livro(livro2)
biblioteca.adicionar_usuario(usuario1)
biblioteca.adicionar_usuario(usuario2)

# Emprestar livro
print(usuario1.pegar_livro(livro1))

# Tentativa de emprestar livro já emprestado
try:
    print(usuario2.pegar_livro(livro1))
except ValueError as e:
    print(e)

# Devolver livro
print(usuario1.retornar_livro(livro1))

# Histórico de empréstimos
print(biblioteca.historico_emprestimos)
