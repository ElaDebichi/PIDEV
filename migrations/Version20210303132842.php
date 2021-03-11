<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20210303132842 extends AbstractMigration
{
    public function getDescription() : string
    {
        return '';
    }

    public function up(Schema $schema) : void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE comment DROP date, CHANGE content content VARCHAR(200) NOT NULL, CHANGE nblikes nblikes INT NOT NULL');
        $this->addSql('ALTER TABLE post ADD content VARCHAR(200) NOT NULL, DROP text, DROP nbviews, CHANGE date date DATETIME NOT NULL COMMENT \'(DC2Type:datetime_immutable)\', CHANGE nblikes nblikes INT NOT NULL');
    }

    public function down(Schema $schema) : void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE comment ADD date DATE DEFAULT NULL, CHANGE content content VARCHAR(200) CHARACTER SET utf8mb4 DEFAULT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE nblikes nblikes INT DEFAULT NULL');
        $this->addSql('ALTER TABLE post ADD text VARCHAR(200) CHARACTER SET utf8mb4 DEFAULT NULL COLLATE `utf8mb4_unicode_ci`, ADD nbviews INT DEFAULT NULL, DROP content, CHANGE date date VARCHAR(10) CHARACTER SET utf8mb4 DEFAULT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE nblikes nblikes INT DEFAULT NULL');
    }
}
