<?php

namespace App\Form;

use App\Entity\Categorie;
use App\Entity\Emploi;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Form\Extension\Core\Type\IntegerType;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class EmploiType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('libelle')
            /*->add('secteur',ChoiceType::class,
                array('choices' => array(
                    'Banque/Assurance/Finance'=>'Banque/Assurance/Finance',
                    'Art/Culture'=>'Art/Culture',
                    'Agroalimentaire/agriculture'=>'Agroalimentaire/agriculture',
                    'Biologie/Chimie'=>'Biologie/Chimie',
                    'Restaurant/Hotellerie'=>'Restaurant/Hotellerie',
                    'Informatique(hardwara/software)'=>'Informatique(hardwara/software)',
                    'informatique ingénieurie'=>'informatique ingénieurie',
                ),
                'required'   => false,)
            )*/
            ->add('poste')
            ->add('dateExpiration', DateType::class, [
                'widget' => 'single_text',
                'format' => 'yyyy-MM-dd',
                'data' => new \DateTime(),
                'attr' => array('class' => 'pickadate'),

            ])
            ->add('typeContrat', ChoiceType::class,
                array('choices' => array(
                    'CDI - Contract of indefinite duration' => 'CDI',
                    'CDD - Fixed-term contract' => 'CDD',
                    'CTT - Temporary employment contract' => 'CTT',
                    'CUI - Single integration contract' => 'CUI',
                    'CAE - Employment support contract' => 'CAE',
                    'CIE - Employment initiative contract' => 'CIE',
                ),
                'required'   => false,
                ))
            ->add('salaire')
            ->add('niveau', ChoiceType::class,
                array('choices' => array(
                    'Baccalaureate' => 'Baccalaureate',
                    'Baccalaureate +3' => 'Baccalaureate +3',
                    'Baccalaureate +5' => 'Baccalaureate +5',
                    'Baccalaureate +6' => 'Baccalaureate +6',
                    'Other' => 'Other',
                ),
                'required'   => false,
                ))
            ->add('description', TextareaType::class)
            ->add('category', EntityType::class, [
                'class' => Categorie::class, 'choice_label' => 'libelle',
                'required'   => false,
            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'data_class' => Emploi::class,
        ]);
    }
}
