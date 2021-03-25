<?php

namespace App\Form;

use App\Entity\Categorie;
use App\Entity\Stage;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class StageType extends AbstractType
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
            ])
            ->add('duree')
            ->add('niveau', ChoiceType::class,
                array('choices' => array(
                    'Baccalaureate' => 'Baccalaureate',
                    'Baccalaureate +3' => 'Baccalaureate +3',
                    'Baccalaureate +5' => 'Baccalaureate +5',
                    'Baccalaureate +6' => 'Baccalaureate +6',
                    'Other' => 'Other',
                ),
                'required' => false,
                ))
            ->add('description',TextareaType::class)
            ->add('category', EntityType::class, [
                'class' => Categorie::class, 'choice_label' => 'libelle',
                'required' => false,
            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'data_class' => Stage::class,
        ]);
    }
}
