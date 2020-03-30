package br.com.netodevel.ioc;

import br.com.netodevel.ioc.annotations.Component;
import br.com.netodevel.ioc.annotations.Inject;

@Component
public class SampleComponent {

    @Inject
    public SampleFieldBean sampleFieldBean;

}
