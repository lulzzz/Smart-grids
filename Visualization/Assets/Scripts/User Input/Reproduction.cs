using UnityEngine;
using UnityEngine.UI;

public enum ButtonType {  Play, Pause, Step }
public class Reproduction : MonoBehaviour
{
    [SerializeField] private ButtonType type;
    private Button button;
    [SerializeField]
    Toggle pause;
    [SerializeField]
    Button step;

    void Start ()
    {
        if (type == ButtonType.Play || type == ButtonType.Pause)
            GetComponent<Toggle>().onValueChanged.AddListener(delegate { Listener(); });
        else
            GetComponent<Button>().onClick.AddListener(delegate { Listener(); });
    }

	public void Listener ()
    {
        switch(type)
        {
            case ButtonType.Play:
                bool on = GetComponent<Toggle>().isOn;
                if (on)
                {
                    pause.interactable = true;
                    if (pause.isOn) step.interactable = true;

                    Manager.Instance.animateAll();
                }
                else
                {
                    pause.isOn = false;
                    pause.interactable = false;
                    step.interactable = false;

                    Manager.Instance.stopAnimating();
                }

                break;


            case ButtonType.Pause:
                on = GetComponent<Toggle>().isOn;
                step.interactable = true;
                Manager.Instance.repeat = GetComponent<Toggle>().isOn;
                
                break;
            case ButtonType.Step:
                Manager.Instance.advanceFrame();
                break;
        }
	
	}
}
