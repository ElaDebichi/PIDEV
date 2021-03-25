var btnPlay = document.querySelector('.container');
var btnCancel = document.querySelector('.container');

var utterance = new SpeechSynthesisUtterance();
var voices = window.speechSynthesis.getVoices();
utterance.voice = voices[1];
utterance.voiceURI = 'native';
utterance.volume = 1; // 0 to 1
utterance.rate = 0.6; // 0.1 to 10
utterance.pitch = 1; //0 to 2
utterance.lang = 'en-US';

utterance.onend = () => {
    window.speechSynthesis.cancel();
};

btnPlay.addEventListener('click', (event) => {
    if(event.target.getAttribute('id') === 'play'){
        utterance.text = event.target.getAttribute('data-content');
        window.speechSynthesis.speak(utterance);
        utterance.onstart = () => {
            console.log('Started..');
            event.preventDefault();
        };
    }
});

btnCancel.addEventListener('click', (event) => {
    if(event.target.getAttribute('id') === 'cancel'){
        window.speechSynthesis.cancel();
        console.log('Finished..');
    }
});