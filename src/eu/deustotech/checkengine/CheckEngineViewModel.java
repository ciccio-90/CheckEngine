package eu.deustotech.checkengine;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import eu.deustotech.clips.Environment;
import eu.deustotech.clips.CLIPSError;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CheckEngineViewModel extends Activity implements OnClickListener {

	private Environment clips;
	private TextView domanda;
	private Button riavvio;
	private Button spiegazione;
	private Button aiuto;
	private Button glossario;
	private Button indietro;
	private Button si;
	private Button no;
	private Button uscita;
	private ArrayList<String> fatti;
	private String preparazioneUtente;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.check_engine_view);
		clips = new Environment();
		domanda = (TextView) findViewById(R.id.textViewOutput);
		riavvio = (Button) findViewById(R.id.buttonRiavvio);
		riavvio.setOnClickListener(this);
		spiegazione = (Button) findViewById(R.id.buttonSpiegazione);
		spiegazione.setOnClickListener(this);
		spiegazione.setEnabled(false);
		aiuto = (Button) findViewById(R.id.buttonAiuto);
		aiuto.setOnClickListener(this);
		aiuto.setEnabled(false);
		glossario = (Button) findViewById(R.id.buttonGlossario);
		glossario.setOnClickListener(this);
		glossario.setEnabled(false);
		indietro = (Button) findViewById(R.id.buttonIndietro);
		indietro.setOnClickListener(this);
		indietro.setEnabled(false);
		si = (Button) findViewById(R.id.buttonSi);
		si.setOnClickListener(this);
		no = (Button) findViewById(R.id.buttonNo);
		no.setOnClickListener(this);
		uscita = (Button) findViewById(R.id.buttonEsci);
		uscita.setOnClickListener(this);
		fatti = new ArrayList<String>();
		preparazioneUtente = new String("");
		generateRuleFileInAppFileDir();
		try {
			clips.load(getFilesDir().getAbsolutePath() + "/knowledge_base.clp");
		} catch (CLIPSError e) {
			JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		}
		clips.run();
		try {
			domanda.setText((String) clips.eval("?*domanda*").getValue());
		} catch (CLIPSError e) {
			JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void generateRuleFileInAppFileDir() {
		FileOutputStream destinationFileStream = null;
		InputStream assetsOriginFileStream = null;
		try {
			destinationFileStream = openFileOutput("knowledge_base.clp", Context.MODE_PRIVATE);
			assetsOriginFileStream = getAssets().open("knowledge_base.clp");
			int aByte;
			while ((aByte = assetsOriginFileStream.read()) != -1) {
				destinationFileStream.write(aByte);
			}			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		} finally {
			try {
				assetsOriginFileStream.close();
				destinationFileStream.close();
			}
			catch (IOException e){
				JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
			}			
		}		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.buttonRiavvio:
				fatti.clear();
				clips.reset();
				clips.run();
				try {
					domanda.setText((String) clips.eval("?*domanda*").getValue());
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
				}
				spiegazione.setEnabled(false);
				aiuto.setEnabled(false);
				glossario.setEnabled(false);
				indietro.setEnabled(false);
				no.setEnabled(true);
				si.setEnabled(true);
				break;
			case R.id.buttonSpiegazione:
				try {
					JOptionPane.showMessageDialog(CheckEngineViewModel.this, (String) clips.eval("?*spiegazione*").getValue(), "Spiegazione", JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
				}
				break;
			case R.id.buttonAiuto:
				switch ((String) domanda.getText()) {
					case "Controlla la tensione della batteria. Il valore misurato e' compreso tra 12.10 e 12.50 volt?":
						try {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, (String) clips.eval("?*aiuto*").getValue(), "Controllo batteria", JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "E' presente il carburante nel serbatoio?":
						try {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, (String) clips.eval("?*aiuto*").getValue(), "Controllo presenza carburante", JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Sostituisci la batteria!":
						try {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, (String) clips.eval("?*aiuto*").getValue(), "Sostituzione batteria", JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Avvia il motore controllando la tensione della batteria. Il valore misurato e' inferiore a 10 volt?":
						try {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, (String) clips.eval("?*aiuto*").getValue(), "Controllo batteria in avviamento", JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Avvia il motore controllando la tensione in arrivo sul motorino di avviamento dal blocchetto chiave. Il valore misurato e' circa 12 volt?":
						try {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, (String) clips.eval("?*aiuto*").getValue(), "Controllo motorino di avviamento", JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Sostituisci la batteria! ":
						try {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, (String) clips.eval("?*aiuto*").getValue(), "Sostituzione batteria", JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Sostituisci il motorino di avviamento!":
						try {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, (String) clips.eval("?*aiuto*").getValue(), "Sostituzione motorino di avviamento", JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Sostituisci la scatola dei fusibili presente nel vano motore!":
						try {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, (String) clips.eval("?*aiuto*").getValue(), "Sostituzione scatola fusibili", JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Controlla il sincronismo tra l'albero motore e l'asse a camme. Il valore misurato sui sensori di giri e fase e' compreso tra 0 e 5 volt?":
						try {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, (String) clips.eval("?*aiuto*").getValue(), "Controllo sincronismo tra albero motore e asse a camme", JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Avvia il motore controllando la pressione del carburante. Il valore misurato e' compreso tra 2 e 2.5 bar?":
						try {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, (String) clips.eval("?*aiuto*").getValue(), "Controllo pressione carburante", JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Avvia il motore controllando la pressione della rampa iniettori. Il valore misurato e' compreso tra 200 e 280 bar?":
						try {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, (String) clips.eval("?*aiuto*").getValue(), "Controllo pressione rampa iniettori", JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Controlla la tensione sul regolatore della pompa di alta pressione. Il valore misurato e' circa 12 volt?":
						try {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, (String) clips.eval("?*aiuto*").getValue(), "Controllo regolatore pompa di alta pressione", JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Nel regolatore della pompa di alta pressione ci sono tracce di smeriglio?":
						try {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, (String) clips.eval("?*aiuto*").getValue(), "Controllo tracce di smeriglio", JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Sostituisci il regolatore della pompa di alta pressione!":
						try {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, (String) clips.eval("?*aiuto*").getValue(), "Sostituzione regolatore pompa di alta pressione", JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Sostituisci la pompa di alta pressione, il suo regolatore, il filtro del carburante e pulisci l'impianto di alimentazione!":
						try {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, (String) clips.eval("?*aiuto*").getValue(), "Sostituzione impianto di alimentazione", JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Il cablaggio della pompa di alta pressione e' in buono stato?":
						try {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, (String) clips.eval("?*aiuto*").getValue(), "Controllo cablaggio pompa di alta pressione", JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Sostituisci il fusibile della pompa di alta pressione!":
						try {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, (String) clips.eval("?*aiuto*").getValue(), "Sostituzione fusibile pompa di alta pressione", JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Sostituisci il cablaggio della pompa di alta pressione!":
						try {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, (String) clips.eval("?*aiuto*").getValue(), "Sostituzione cablaggio pompa di alta pressione", JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Controlla gli iniettori. Perdono tutti del carburante?":
						try {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, (String) clips.eval("?*aiuto*").getValue(), "Controllo perdite iniettori", JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Sostituisci tutti gli iniettori!":
						try {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, (String) clips.eval("?*aiuto*").getValue(), "Sostituzione iniettori", JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Sostituisci solo gli iniettori che perdono del carburante!":
						try {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, (String) clips.eval("?*aiuto*").getValue(), "Sostituzione iniettori", JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Controlla la tensione in arrivo sulla pompa di bassa pressione. Il valore misurato e' circa 12 volt?":
						try {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, (String) clips.eval("?*aiuto*").getValue(), "Controllo pompa di bassa pressione", JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Sostituisci la pompa di bassa pressione!":
						try {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, (String) clips.eval("?*aiuto*").getValue(), "Sostituzione pompa di bassa pressione", JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Il cablaggio della pompa di bassa pressione e' in buono stato?":
						try {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, (String) clips.eval("?*aiuto*").getValue(), "Controllo cablaggio pompa di bassa pressione", JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Sostituisci il cablaggio della pompa di bassa pressione!":
						try {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, (String) clips.eval("?*aiuto*").getValue(), "Sostituzione cablaggio pompa di bassa pressione", JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Sostituisci il fusibile della pompa di bassa pressione!":
						try {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, (String) clips.eval("?*aiuto*").getValue(), "Sostituzione fusibile pompa di bassa pressione", JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "La cinghia di distribuzione e' in buono stato?":
						try {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, (String) clips.eval("?*aiuto*").getValue(), "Controllo kit distribuzione", JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Sostituisci il kit distribuzione!":
						try {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, (String) clips.eval("?*aiuto*").getValue(), "Sostituzione kit distribuzione", JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Sostituisci i sensori di giri e fase!":
						try {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, (String) clips.eval("?*aiuto*").getValue(), "Sostituzione sensori di giri e fase", JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Il motore gira?":
						try {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, (String) clips.eval("?*aiuto*").getValue(), "Controllo avvio del motore", JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Il livello dell'olio motore e' buono?":
						try {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, (String) clips.eval("?*aiuto*").getValue(), "Controllo livello olio motore", JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Rabbocca l'olio motore!":
						try {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, (String) clips.eval("?*aiuto*").getValue(), "Rabbocco olio motore", JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Controlla la tensione sotto il termocontatto dell'olio motore. Il valore misurato e' circa 12 volt?":
						try {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, (String) clips.eval("?*aiuto*").getValue(), "Controllo termocontatto dell'olio motore", JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Controlla la pressione dell'olio motore. Il valore misurato e' compreso tra 0.2 e 0.4 bar?":
						try {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, (String) clips.eval("?*aiuto*").getValue(), "Controllo pressione dell'olio motore", JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Sostituisci il termocontatto dell'olio motore!":
						try {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, (String) clips.eval("?*aiuto*").getValue(), "Sostituzione termocontatto dell'olio motore", JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Sostituisci la pompa dell'olio motore!":
						try {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, (String) clips.eval("?*aiuto*").getValue(), "Sostituzione pompa dell'olio motore", JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Il fusibile del termocontatto dell'olio motore e' bruciato?":
						try {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, (String) clips.eval("?*aiuto*").getValue(), "Verifca fusibile del termocontatto dell'olio motore", JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Sostituisci il filo conduttore del termocontatto dell'olio motore!":
						try {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, (String) clips.eval("?*aiuto*").getValue(), "Sostituzione filo conduttore del termocontatto dell'olio motore", JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Sostituisci il fusibile del termocontatto dell'olio motore!":
						try {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, (String) clips.eval("?*aiuto*").getValue(), "Sostituzione fusibile del termocontatto dell'olio motore", JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Sostituisci la cinghia accessori!":
						try {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, (String) clips.eval("?*aiuto*").getValue(), "Sostituzione cinghia accessori", JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Sostituisci l'alternatore!":
						try {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, (String) clips.eval("?*aiuto*").getValue(), "Sostituzione alternatore", JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					default:
						break;
				}
				break;
			case R.id.buttonGlossario:
				try {
					JOptionPane.showMessageDialog(CheckEngineViewModel.this, Html.fromHtml((String) clips.eval("?*glossario*").getValue()).toString(), "Glossario", JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
				}
				break;
			case R.id.buttonIndietro:
				String d = new String("");
				try {
					preparazioneUtente = (String) clips.eval("?*preparazione-utente*").getValue();
					d = (String) clips.eval("?*domanda*").getValue();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
				}
				if (preparazioneUtente.equals("discreta") && d.equals("Il motore gira?"))
					fatti.remove(fatti.size() - 1);
				fatti.remove(fatti.size() - 1);
				clips.reset();
				clips.run();
				for (int i = 0; i < fatti.size(); i++) {
					try {
						clips.assertString(fatti.get(i));
					} catch (CLIPSError e) {
						JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
					}
					clips.run();
				}
				try {
					domanda.setText((String) clips.eval("?*domanda*").getValue());
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
				}
				if (domanda.getText().equals("Il veicolo si avvia?")) {
					spiegazione.setEnabled(false);
					aiuto.setEnabled(false);
					glossario.setEnabled(false);
					indietro.setEnabled(false);
				}
				else if (domanda.getText().equals("La spia dell'olio motore e' accesa?")) {
					spiegazione.setEnabled(false);
					aiuto.setEnabled(false);
				}
				else if (domanda.getText().equals("La spia dell'alternatore e' accesa?")) {
					spiegazione.setEnabled(false);
					aiuto.setEnabled(false);
				}
				else if (domanda.getText().equals("La temperatura dell'acqua motore e' salita oltre 120 °C?")) 
					aiuto.setEnabled(false);
				else if (domanda.getText().equals("Il livello dell'olio motore e' buono?")) {
					spiegazione.setEnabled(true);
					aiuto.setEnabled(true);
				}
				else if (domanda.getText().equals("E' presente il carburante nel serbatoio?")) {
					spiegazione.setEnabled(true);
					aiuto.setEnabled(true);
				}
				no.setEnabled(true);
				si.setEnabled(true);
				break;
			case R.id.buttonNo:
				switch ((String) domanda.getText()) {
					case "Hai mai lavorato in un'autofficina meccanica?":
						try {
							clips.assertString("(livello-base non superato)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(livello-base non superato)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*domanda*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Sei un meccanico?":
						try {
							clips.assertString("(livello-base non superato)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(livello-base non superato)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*domanda*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Hai mai effettuato una riparazione ad un veicolo?":
						try {
							clips.assertString("(livello-base non superato)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(livello-base non superato)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*domanda*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Hai mai sostituito un pneumatico?":
						try {
							clips.assertString("(livello-base non superato)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(livello-base non superato)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*domanda*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Hai mai sostituito una lampada ad un faro di un veicolo?":
						try {
							clips.assertString("(livello-base non superato)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(livello-base non superato)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*domanda*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Hai mai sostituito l'olio motore?":
						try {
							clips.assertString("(livello-intermedio non superato)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(livello-intermedio non superato)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*domanda*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Hai mai sostituito un fusibile?":
						try {
							clips.assertString("(livello-intermedio non superato)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(livello-intermedio non superato)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*domanda*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Hai mai sostituito i pattini dei freni?":
						try {
							clips.assertString("(livello-intermedio non superato)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(livello-intermedio non superato)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*domanda*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Hai mai sostituito la cinghia accessori?":
						try {
							clips.assertString("(livello-intermedio non superato)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(livello-intermedio non superato)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*domanda*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Hai mai sostituito la batteria?":
						try {
							clips.assertString("(livello-intermedio non superato)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(livello-intermedio non superato)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*domanda*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Hai mai sostituito il kit distribuzione?":
						try {
							clips.assertString("(livello-alto non superato)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(livello-alto non superato)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*domanda*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Hai mai sostituito un iniettore?":
						try {
							clips.assertString("(livello-alto non superato)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(livello-alto non superato)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*domanda*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Hai mai sostituito la pompa di alta pressione?":
						try {
							clips.assertString("(livello-alto non superato)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(livello-alto non superato)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*domanda*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Hai mai sostituito l'alternatore?":
						try {
							clips.assertString("(livello-alto non superato)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(livello-alto non superato)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*domanda*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Hai mai sostituito il motorino di avviamento?":
						try {
							clips.assertString("(livello-alto non superato)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(livello-alto non superato)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*domanda*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Il veicolo si avvia?":
						try {
							preparazioneUtente = (String) clips.eval("?*preparazione-utente*").getValue();
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						try {
							clips.assertString("(veicolo non parte)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(veicolo non parte)");
						clips.run();
						if (preparazioneUtente.equals("insufficiente")) {
							try {
								domanda.setText((String) clips.eval("?*soluzione*").getValue());
							} catch (Exception e1) {
								JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
							}
							glossario.setEnabled(true);
							indietro.setEnabled(true);
							no.setEnabled(false);
							si.setEnabled(false);
						}
						else if (preparazioneUtente.equals("discreta")) {
							try {
								clips.assertString("(presenza carburante)");
							} catch (CLIPSError e) {
								JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
							}
							fatti.add("(presenza carburante)");
							clips.run();
							try {
								domanda.setText((String) clips.eval("?*domanda*").getValue());
							} catch (Exception e1) {
								JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
							}
							spiegazione.setEnabled(true);
							aiuto.setEnabled(true);
							glossario.setEnabled(true);
							indietro.setEnabled(true);
						}
						else {
							try {
								domanda.setText((String) clips.eval("?*domanda*").getValue());
							} catch (Exception e1) {
								JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
							}
							spiegazione.setEnabled(true);
							aiuto.setEnabled(true);
							glossario.setEnabled(true);
							indietro.setEnabled(true);
						}
						break;
					case "E' presente il carburante nel serbatoio?":
						try {
							clips.assertString("(assenza carburante)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(assenza carburante)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*soluzione*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						spiegazione.setEnabled(false);
						aiuto.setEnabled(false);
						si.setEnabled(false);
						no.setEnabled(false);
						break;
					case "Il motore gira?":
						try {
							clips.assertString("(motore non gira)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(motore non gira)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*domanda*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Controlla il sincronismo tra l'albero motore e l'asse a camme. Il valore misurato sui sensori di giri e fase e' compreso tra 0 e 5 volt?":
						try {
							clips.assertString("(tensione sensori ko)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(tensione sensori ko)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*domanda*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Avvia il motore controllando la pressione del carburante. Il valore misurato e' compreso tra 2 e 2.5 bar?":
						try {
							clips.assertString("(pressione carburante ko)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(pressione carburante ko)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*domanda*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Avvia il motore controllando la pressione della rampa iniettori. Il valore misurato e' compreso tra 200 e 280 bar?":
						try {
							clips.assertString("(pressione iniettori ko)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(pressione iniettori ko)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*domanda*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Controlla la tensione sul regolatore della pompa di alta pressione. Il valore misurato e' circa 12 volt?":
						try {
							clips.assertString("(tensione pompa-hp ko)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(tensione pompa-hp ko)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*domanda*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Nel regolatore della pompa di alta pressione ci sono tracce di smeriglio?":
						try {
							clips.assertString("(assenza smeriglio)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(assenza smeriglio)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*soluzione*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						si.setEnabled(false);
						no.setEnabled(false);
						break;
					case "Il cablaggio della pompa di alta pressione e' in buono stato?":
						try {
							clips.assertString("(cablaggio pompa-hp ko)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(cablaggio pompa-hp ko)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*soluzione*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						si.setEnabled(false);
						no.setEnabled(false);
						break;
					case "Controlla gli iniettori. Perdono tutti del carburante?":
						try {
							clips.assertString("(ritorno carburante diverso)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(ritorno carburante diverso)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*soluzione*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						si.setEnabled(false);
						no.setEnabled(false);
						break;
					case "Controlla la tensione in arrivo sulla pompa di bassa pressione. Il valore misurato e' circa 12 volt?":
						try {
							clips.assertString("(tensione pompa-lp ko)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(tensione pompa-lp ko)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*domanda*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Il cablaggio della pompa di bassa pressione e' in buono stato?":
						try {
							clips.assertString("(cablaggio pompa-lp ko)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(cablaggio pompa-lp ko)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*soluzione*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						si.setEnabled(false);
						no.setEnabled(false);
						break;
					case "La cinghia di distribuzione e' in buono stato?":
						try {
							clips.assertString("(distribuzione ko)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(distribuzione ko)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*soluzione*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						si.setEnabled(false);
						no.setEnabled(false);
						break;
					case "Controlla la tensione della batteria. Il valore misurato e' compreso tra 12.10 e 12.50 volt?":
						try {
							clips.assertString("(tensione batteria ko)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(tensione batteria ko)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*soluzione*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						si.setEnabled(false);
						no.setEnabled(false);
						break;
					case "Avvia il motore controllando la tensione della batteria. Il valore misurato e' inferiore a 10 volt?":
						try {
							clips.assertString("(tensione avviamento ok)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(tensione avviamento ok)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*domanda*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Avvia il motore controllando la tensione in arrivo sul motorino di avviamento dal blocchetto chiave. Il valore misurato e' circa 12 volt?":
						try {
							clips.assertString("(tensione blocchetto chiave ko)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(tensione blocchetto chiave ko)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*soluzione*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						si.setEnabled(false);
						no.setEnabled(false);
						break;
					case "Il livello dell'olio motore e' buono?":
						try {
							clips.assertString("(livello olio basso)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(livello olio basso)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*soluzione*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						si.setEnabled(false);
						no.setEnabled(false);
						break;
					case "Controlla la pressione dell'olio motore. Il valore misurato e' compreso tra 0.2 e 0.4 bar?":
						try {
							clips.assertString("(pressione olio bassa)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(pressione olio bassa)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*soluzione*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						no.setEnabled(false);
						si.setEnabled(false);
						break;
					case "Controlla la tensione sotto il termocontatto dell'olio motore. Il valore misurato e' circa 12 volt?":
						try {
							clips.assertString("(tensione termocontatto non buona)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(tensione termocontatto non buona)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*domanda*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Il fusibile del termocontatto dell'olio motore e' bruciato?":
						try {
							clips.assertString("(fusibile termocontatto buono)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(fusibile termocontatto buono)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*soluzione*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						no.setEnabled(false);
						si.setEnabled(false);
						break;
					case "La spia dell'olio motore e' accesa?":
						try {
							clips.assertString("(spia olio spenta)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(spia olio spenta)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*domanda*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "La spia dell'alternatore e' accesa?":
						try {
							clips.assertString("(spia alternatore spenta)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(spia alternatore spenta)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*soluzione*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						no.setEnabled(false);
						si.setEnabled(false);
						break;
					case "La temperatura dell'acqua motore e' salita oltre 120 °C?":
						try {
							clips.assertString("(temperatura acqua normale)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(temperatura acqua normale)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*soluzione*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						aiuto.setEnabled(true);
						no.setEnabled(false);
						si.setEnabled(false);
						break;
					default:
						break;
				}
				break;
			case R.id.buttonSi:
				switch ((String) domanda.getText()) {
					case "Hai mai lavorato in un'autofficina meccanica?":
						try {
							clips.assertString("(livello-base superato)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(livello-base superato)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*domanda*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Sei un meccanico?":
						try {
							clips.assertString("(livello-base superato)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(livello-base superato)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*domanda*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Hai mai effettuato una riparazione ad un veicolo?":
						try {
							clips.assertString("(livello-base superato)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(livello-base superato)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*domanda*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Hai mai sostituito un pneumatico?":
						try {
							clips.assertString("(livello-base superato)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(livello-base superato)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*domanda*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Hai mai sostituito una lampada ad un faro di un veicolo?":
						try {
							clips.assertString("(livello-base superato)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(livello-base superato)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*domanda*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Hai mai sostituito l'olio motore?":
						try {
							clips.assertString("(livello-intermedio superato)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(livello-intermedio superato)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*domanda*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Hai mai sostituito un fusibile?":
						try {
							clips.assertString("(livello-intermedio superato)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(livello-intermedio superato)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*domanda*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Hai mai sostituito i pattini dei freni?":
						try {
							clips.assertString("(livello-intermedio superato)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(livello-intermedio superato)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*domanda*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Hai mai sostituito la cinghia accessori?":
						try {
							clips.assertString("(livello-intermedio superato)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(livello-intermedio superato)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*domanda*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Hai mai sostituito la batteria?":
						try {
							clips.assertString("(livello-intermedio superato)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(livello-intermedio superato)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*domanda*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Hai mai sostituito il kit distribuzione?":
						try {
							clips.assertString("(livello-alto superato)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(livello-alto superato)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*domanda*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Hai mai sostituito un iniettore?":
						try {
							clips.assertString("(livello-alto superato)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(livello-alto superato)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*domanda*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Hai mai sostituito la pompa di alta pressione?":
						try {
							clips.assertString("(livello-alto superato)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(livello-alto superato)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*domanda*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Hai mai sostituito l'alternatore?":
						try {
							clips.assertString("(livello-alto superato)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(livello-alto superato)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*domanda*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Hai mai sostituito il motorino di avviamento?":
						try {
							clips.assertString("(livello-alto superato)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(livello-alto superato)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*domanda*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Il veicolo si avvia?":
						try {
							clips.assertString("(veicolo parte)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(veicolo parte)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*domanda*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						glossario.setEnabled(true);
						indietro.setEnabled(true);
						break;
					case "E' presente il carburante nel serbatoio?":
						try {
							clips.assertString("(presenza carburante)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(presenza carburante)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*domanda*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Il motore gira?":
						try {
							clips.assertString("(motore gira)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(motore gira)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*domanda*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Controlla il sincronismo tra l'albero motore e l'asse a camme. Il valore misurato sui sensori di giri e fase e' compreso tra 0 e 5 volt?":
						try {
							clips.assertString("(tensione sensori ok)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(tensione sensori ok)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*domanda*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Avvia il motore controllando la pressione del carburante. Il valore misurato e' compreso tra 2 e 2.5 bar?":
						try {
							clips.assertString("(pressione carburante ok)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(pressione carburante ok)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*domanda*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Avvia il motore controllando la pressione della rampa iniettori. Il valore misurato e' compreso tra 200 e 280 bar?":
						try {
							clips.assertString("(pressione iniettori ok)");
						} catch (CLIPSError e2) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e2.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(pressione iniettori ok)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*domanda*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Controlla la tensione sul regolatore della pompa di alta pressione. Il valore misurato e' circa 12 volt?":
						try {
							clips.assertString("(tensione pompa-hp ok)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(tensione pompa-hp ok)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*domanda*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Nel regolatore della pompa di alta pressione ci sono tracce di smeriglio?":
						try {
							clips.assertString("(presenza smeriglio)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(presenza smeriglio)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*soluzione*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						si.setEnabled(false);
						no.setEnabled(false);
						break;
					case "Il cablaggio della pompa di alta pressione e' in buono stato?":
						try {
							clips.assertString("(cablaggio pompa-hp ok)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(cablaggio pompa-hp ok)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*soluzione*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						si.setEnabled(false);
						no.setEnabled(false);
						break;
					case "Controlla gli iniettori. Perdono tutti del carburante?":
						try {
							clips.assertString("(ritorno carburante uguale)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(ritorno carburante uguale)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*soluzione*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						si.setEnabled(false);
						no.setEnabled(false);
						break;
					case "Controlla la tensione in arrivo sulla pompa di bassa pressione. Il valore misurato e' circa 12 volt?":
						try {
							clips.assertString("(tensione pompa-lp ok)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(tensione pompa-lp ok)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*soluzione*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						si.setEnabled(false);
						no.setEnabled(false);
						break;
					case "Il cablaggio della pompa di bassa pressione e' in buono stato?":
						try {
							clips.assertString("(cablaggio pompa-lp ok)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(cablaggio pompa-lp ok)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*soluzione*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						si.setEnabled(false);
						no.setEnabled(false);
						break;
					case "La cinghia di distribuzione e' in buono stato?":
						try {
							clips.assertString("(distribuzione ok)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(distribuzione ok)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*soluzione*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						si.setEnabled(false);
						no.setEnabled(false);
						break;
					case "Controlla la tensione della batteria. Il valore misurato e' compreso tra 12.10 e 12.50 volt?":
						try {
							clips.assertString("(tensione batteria ok)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(tensione batteria ok)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*domanda*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Avvia il motore controllando la tensione della batteria. Il valore misurato e' inferiore a 10 volt?":
						try {
							clips.assertString("(tensione avviamento ko)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(tensione avviamento ko)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*soluzione*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						si.setEnabled(false);
						no.setEnabled(false);
						break;
					case "Avvia il motore controllando la tensione in arrivo sul motorino di avviamento dal blocchetto chiave. Il valore misurato e' circa 12 volt?":
						try {
							clips.assertString("(tensione blocchetto chiave ok)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(tensione blocchetto chiave ok)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*soluzione*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						si.setEnabled(false);
						no.setEnabled(false);
						break;
					case "La spia dell'olio motore e' accesa?":
						try {
							clips.assertString("(spia olio accesa)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(spia olio accesa)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*domanda*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						spiegazione.setEnabled(true);
						aiuto.setEnabled(true);
						break;
					case "Il livello dell'olio motore e' buono?":
						try {
							preparazioneUtente = (String) clips.eval("?*preparazione-utente*").getValue();
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						try {
							clips.assertString("(livello olio buono)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(livello olio buono)");
						clips.run();
						if (preparazioneUtente.equals("insufficiente")) {
							try {
								domanda.setText((String) clips.eval("?*soluzione*").getValue());
							} catch (Exception e1) {
								JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
							}
							spiegazione.setEnabled(false);
							aiuto.setEnabled(false);
							no.setEnabled(false);
							si.setEnabled(false);
						}
						else {
							try {
								domanda.setText((String) clips.eval("?*domanda*").getValue());
							} catch (Exception e1) {
								JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
							}
						}
						break;
					case "Controlla la tensione sotto il termocontatto dell'olio motore. Il valore misurato e' circa 12 volt?":
						try {
							clips.assertString("(tensione termocontatto buona)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(tensione termocontatto buona)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*domanda*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						break;
					case "Controlla la pressione dell'olio motore. Il valore misurato e' compreso tra 0.2 e 0.4 bar?":
						try {
							clips.assertString("(pressione olio buona)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(pressione olio buona)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*soluzione*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						no.setEnabled(false);
						si.setEnabled(false);
						break;
					case "Il fusibile del termocontatto dell'olio motore e' bruciato?":
						try {
							clips.assertString("(fusibile termocontatto fuso)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(fusibile termocontatto fuso)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*soluzione*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						no.setEnabled(false);
						si.setEnabled(false);
						break;
					case "La spia dell'alternatore e' accesa?":
						try {
							preparazioneUtente = (String) clips.eval("?*preparazione-utente*").getValue();
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						try {
							clips.assertString("(spia alternatore accesa)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(spia alternatore accesa)");
						clips.run();
						if (preparazioneUtente.equals("insufficiente")) {
							try {
								domanda.setText((String) clips.eval("?*soluzione*").getValue());
							} catch (Exception e1) {
								JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
							}
							no.setEnabled(false);
							si.setEnabled(false);
						}
						else {
							try {
								domanda.setText((String) clips.eval("?*domanda*").getValue());
							} catch (Exception e1) {
								JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
							}
							spiegazione.setEnabled(true);
						}
						break;
					case "La temperatura dell'acqua motore e' salita oltre 120 °C?":
						try {
							clips.assertString("(temperatura acqua elevata)");
						} catch (CLIPSError e) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						fatti.add("(temperatura acqua elevata)");
						clips.run();
						try {
							domanda.setText((String) clips.eval("?*soluzione*").getValue());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(CheckEngineViewModel.this, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						}
						aiuto.setEnabled(true);
						no.setEnabled(false);
						si.setEnabled(false);
						break;
					default:
						break;
				}
				break;
			case R.id.buttonEsci:
				AlertDialog.Builder builder = new AlertDialog.Builder(CheckEngineViewModel.this); 
				builder.setTitle("Attenzione").setMessage("Vuoi davvero chiudere l'App?").setCancelable(false).setPositiveButton("Si", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int whichButton) {
						finish();
						System.exit(1);
					}
					
				}).setNegativeButton("No", new DialogInterface.OnClickListener() {
						
					public void onClick(DialogInterface dialog, int whichButton) {} 
						
				}).create().show();
				break;
			default:
				break;
		}
	}
	
	static class JOptionPane {
		
		public static final int ERROR_MESSAGE = 0;
		public static final int INFORMATION_MESSAGE = 1;

        public static void showMessageDialog(Context context, String errorMessage, String errorTitle, int errorType) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context); 
            builder.setTitle(errorTitle).setMessage(errorMessage).setCancelable(false).setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            	
            	public void onClick(DialogInterface dialog, int id) {}
            
            }).create().show();
        }
        
	}
   
}