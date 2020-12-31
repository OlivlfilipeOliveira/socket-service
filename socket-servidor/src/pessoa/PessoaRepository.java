package pessoa;

import java.util.ArrayList;

public class PessoaRepository {
	private static ArrayList<Pessoa> pessoas = new ArrayList<Pessoa>();

	public PessoaRepository() {
		carregarTeste();
	}

	public boolean insertPessoa(Pessoa p) {
		if (p != null) {
			pessoas.add(p);
			return true;
		} else {
			return false;
		}
	}

	public Pessoa findPessoaById(int id) {
		Pessoa p = null;

		for (Pessoa pessoa : pessoas) {
			if (pessoa.getId() == id) {
				p = pessoa;
				break;
			}
		}

		return p;
	}

	public boolean removePessoaById(int id) {
		for (Pessoa pessoa : pessoas) {
			if (pessoa.getId() == id) {
				pessoas.remove(id);
				return true;
			}
		}
		return false;
	}

	public boolean updateNamePessoa(int id, String newName) {
		for (Pessoa pessoa : pessoas) {
			if (pessoa.getId() == id) {
				pessoa.setNome(newName);
				return true;
			}
		}
		return false;
	}

	public static void carregarTeste() {
		Pessoa p1 = new Pessoa(1, "Biu", "Oliveira", "M");
		Pessoa p2 = new Pessoa(2, "Zé", "Oliveira", "M");
		Pessoa p3 = new Pessoa(3, "Elk", "Oliveira", "F");

		pessoas.add(p1);
		pessoas.add(p2);
		pessoas.add(p3);
	}

}
