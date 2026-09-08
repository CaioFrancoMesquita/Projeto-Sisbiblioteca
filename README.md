# Projeto-Sisbiblioteca

Nome do integrante: Caio Franco Mesquita
RA: 1630482511004

Projeto desenvolvido como nota da P1 de Programação de Scripts I (Back-End). Abaixo estão listadas as 5 alterações feitas no programa:

1 - orphanRemoval = true
Faz com que um livro seja excluído automaticamente do banco quando ele é removido da lista de livros do autor.

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

2 - Métodos adicionarLivro() e removerLivro()
Esses métodos ficam na classe Autor e mantêm o relacionamento entre Autor e Livro sincronizado.

Ao adicionar um livro, ele é incluído na lista do autor e o recebe. Isso mantém os dois lados do relacionamento atualizados, sem esse método, poderia acontecer de o livro aparecer na lista do autor, mas ainda possuir outro autor internamente.

Ao remover um livro, ele é retirado da lista do autor, que fica definido como null. Como a associação possui orphanRemoval = true, o JPA entende que o livro foi abandonado pelo autor e remove esse registro do banco de dados ao final da transição.

Em resumo, esses métodos evitam alterações incompletas no relacionamento e garantem que autor, livro e banco de dados permaneçam consistentes.

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

3 - JOIN FETCH
É usado para buscar uma entidade e seus relacionamentos em uma única consulta SQL.

List<Autor> findAllWithLivros: busca todos os autores junto com seus livros.

List<Livro> findAllWithAutor: busca todos os livros junto com seus autores.

Isso evita o problema n + 1, no qual o sistema faria uma consulta para buscar os autores e depois várias consultas adicionais para buscar os livros de cada autor. Em resumo, JOIN FETCH melhora o carregamento dos relacionamentos e evita erros quando a lista livros está configurada como LAZY e a transação já foi encerrada.

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------

4 - AutorService, LivroService com @Transactional
As camadas organizam as regras de negócio da aplicação.

O Controller recebe as requisições HTTP, mas não deve concentrar a lógica. Ele chama o Service, que busca autores e livros; cadastram, atualiza e remove registros; valida relacionamentos, como verificar se o autor existe; coordena operações entre entidades. O controller apenas recebe os dados, o service localiza o autor, cria o livro e salva tudo corretamente.

@Transactional: A anotação indica que o método deve ser executado dentro de uma transação do banco de dados. Em operações de alteração, todas elas são confirmadas juntas. Se ocorrer, um erro no meio, a transação pode ser desfeita, evitando dados incompletos. Também permite alterar uma entidade sem chamar save() explicitamente em todos os casos, pois o JPA acompanha as entidades gerenciadas.

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------

5 - API REST
Permite acessar o sistema por requisições HTTP, sem usar o meu do terminal. Ela disponibiliza endpoints para autores e livros como GET; GET {id]; POST; PUT e DELETE.

A API usa a camada Service para aplicar as regras de negócio e a camada Repository para acessar o banco de dados. O @Transactional garante que cada operação no banco seja concluída corretamente ou desfeita em caso de erro.

