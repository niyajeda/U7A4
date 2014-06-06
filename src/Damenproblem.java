import java.util.ArrayList;

/**
 * Created by br0ce on 04.06.14.
 */
public class Damenproblem
{
	final static boolean TESTRUN = false;
	private int n = 4;
	private int dN;
	private int count;
	private int gezaelteDamen;
	private int[][] feld;
	private ArrayList<Boolean> mSpalte;
	private ArrayList<Boolean> mSteigend;
	private ArrayList<Boolean> mFallend;

	public Damenproblem(int d)
	{
		if(d < 4) { throw new IllegalArgumentException("Cheating is not welcome!"); }

		n = d;
		dN = (d - 1);
		gezaelteDamen = 0;
		count = 0;
		feld = new int[d][d];

		mSpalte = new ArrayList<Boolean>();
		for(int i = 0; i < n; ++i)
			mSpalte.add(true);

		mSteigend = new ArrayList<Boolean>();
		for(int i = 0; i < (2 * n); ++i)
			mSteigend.add(true);

		mFallend = new ArrayList<Boolean>();
		for(int i = 0; i < (2 * dN); ++i)
			mFallend.add(true);
	}

	public void ausgabe()
	{
		for(int i = 0; i < feld.length; ++i)
		{
			System.out.println();
			for(int j = 0; j < feld[0].length; ++j)
				System.out.print(feld[i][j] + " ");
		}
		System.out.println("\n");
	}

	public boolean feldFrei(int spalte, int zeile)
	{
		return (mSpalte.get(spalte) && mSteigend.get(zeile + spalte) && mFallend.get(zeile - spalte + dN));
	}

	public void durchgangStarten()
	{
		plaziere(0);
		if(TESTRUN)
			System.out.println("Es wurden " + count + " Positionen probiert.");
	}

	private boolean plaziere(int zeile)
	{
		for(int spalte = 0; spalte < n; ++spalte)
		{
			if(feldFrei(spalte, zeile))
			{
				setzteDame(spalte, zeile);
				if(zeile < (n - 1))
				{
					if(plaziere(zeile + 1))
						return true;
					else
						entferneDame(spalte, zeile);
				} else
					return true;
			}
		}
		return false;
	}

	public void setzteDame(int spalte, int zeile)
	{
		feld[zeile][spalte] = 1;
		if(TESTRUN) ausgabe();
		count++;
		mSpalte.set(spalte, false);
		mSteigend.set(zeile + spalte, false);
		mFallend.set(zeile - spalte + dN, false);
	}

	public void entferneDame(int spalte, int zeile)
	{
		feld[zeile][spalte] = 0;
		if(TESTRUN) ausgabe();
		mSpalte.set(spalte, true);
		mSteigend.set(zeile + spalte, true);
		mFallend.set(zeile - spalte + dN, true);
	}

	public boolean korrektPlatziert()
	{
		for(int i = 0; i < n; i++)
		{
			for(int j = 0; j < n; j++)
			{
				if(this.feld[i][j] == 1)
				{
					//Zeile durchsuchen
					for(int k = 0; k < n; k++)
					{
						if(feld[i][k] == 1 && (k != j))
							return false;
					}
					//Spalte durchsuchen
					for(int k = 0; k < n; k++)
					{
						if(feld[k][j] == 1 && (k != i))
							return false;
					}
					//Diagonalen durchsuchen
					//steigende Diagonale -- Summe der Diagonale ist immer i + j
					for(int k = 0; k < n; k++)
					{
						for(int l = 0; l < n; l++)
						{
							if((l + k) == (j + i) && feld[k][l] == 1 && (k != i) && (l != j))
								return false;
						}
					}
					//fallende Diagonale -- Spalte - Zeile ist immer j - i
					for(int k = 0; k < n; k++)
					{
						for(int l = 0; l < n; l++)
						{
							if((l - k) == (j - i) && feld[k][l] == 1 && (k != i) && (l != j))
								return false;
						}
					}
					gezaelteDamen++;
				}
			}
		}
		return (gezaelteDamen == n) ? true : false;
	}

	static public void main(String[] arg)
	{
		Damenproblem dOk = new Damenproblem(4);
		dOk.durchgangStarten();

		if(dOk.korrektPlatziert())
		{
			System.out.println("dOk ist eine moegliche Loesung!:");
		} else
		{
			System.out.println("dOk ist keine erlaubte Loesung:");
		}

		dOk.ausgabe();
	}
}
